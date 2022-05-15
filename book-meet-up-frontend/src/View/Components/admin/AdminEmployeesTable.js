import React, {useEffect, useState} from 'react'
import {withTranslation} from 'react-i18next'
import axios from "../util/ApiUtil";
import DefaultLoader from "../ui/Loader";
import {setEditUserEmail, setEditUserRole} from "../util/LocalStorageUtils";
import {EMPLOYEE} from "../util/Constants";
import {formatEmployeeData} from "../util/DataFormattingUtil";
import DataTableComponent from "../ui/DataTable";
import {sortById} from "../util/TableUtil";

function AdminEmployeesTable() {

    const [isLoaded, setIsLoaded] = useState(false);
    const [employees, setEmployees] = useState([]);

    useEffect(() => {
        axios.get(`/employees`)
            .then(result => {
                const data = result.data;
                setEmployees(data.map(formatEmployeeData))
                setIsLoaded(true)
            })
    }, []);

    const columns = React.useMemo(() => {
        const columns = [];
        columns.push({
            accessor: 'isLocked',
            toTranslate: true,
            applyCustomStyle: 'lockStatusStyle'
        })
        columns.push({
            Header: 'Phone',
            accessor: 'phoneNumber'
        })
        columns.push({
            Header: 'Email',
            accessor: 'email',
            insertBreak: true
        })
        return columns;
    }, [])

    function editEntity(email) {
        setEditUserEmail(email);
        setEditUserRole(EMPLOYEE);
        window.location.href = "./edit";
    }

    const lockUser = async email => {
        setIsLoaded(false);
        await axios.post(`/admin/lock-user/${email}`);
        window.location.reload();
    }

    const operations = [
        {
            "name": "Edit",
            "onClick": editEntity,
            "className": "btn",
            "onClickPassParameter": "email"
        },
        {
            "name": "UnlockLock",
            "onClick": lockUser,
            "className": "btn btn-danger-warning",
            "onClickPassParameter": "email"
        },
        {
            "name": "Delete",
            "className": "btn btn-danger",
            "onClickPassParameter": "id",
            "url": "employees/{id}",
        }
    ]
    if (!isLoaded) return <DefaultLoader isCentered={true} height={425} width={425}/>;
    return <DataTableComponent displayData={employees} displayColumns={columns} operations={operations}
                               tableName={"Employees"} addEntityUrl={'/signup'} sorter={sortById}/>;
}

export default withTranslation()(AdminEmployeesTable);
