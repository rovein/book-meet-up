import React, {useEffect, useState} from "react";

import Button from "./Button";
import {useTranslation, withTranslation} from "react-i18next";
import axios from '../util/ApiUtil';
import DefaultLoader from "./Loader";
import _ from "lodash";
import {confirmAlert} from "react-confirm-alert";
import 'react-confirm-alert/src/react-confirm-alert.css';

function Table({columns, data, operations, tableName, addEntityUrl}) {
    const [isLoaded, setIsLoaded] = useState(true)

    const {t} = useTranslation();

    function handleDeleteOperation(url, elementId, callback) {
        confirmAlert({
            title: t("AreYouSure"),
            message: t("NotRecover"),
            buttons: [
                {
                    label: t("Delete"),
                    onClick: () => deleteEntity(url.replace("{id}", elementId), elementId, callback)
                },
                {
                    label: t("Cancel")
                }
            ],
            closeOnEscape: true,
            closeOnClickOutside: true,
        });
    }

    function deleteEntity(url, id, deleteCallback) {
        setIsLoaded(false)
        axios.delete(`/${url}`)
            .then(_ => {
                deleteCallback(id)
                setIsLoaded(true)
            })
            .catch(e => {
                confirmAlert({
                    title: t("Error"),
                    message: t("DeleteErrorMessage"),
                    buttons: [
                        {
                            label: t("Ok")
                        }
                    ],
                    closeOnEscape: true,
                    closeOnClickOutside: true,
                });
                setIsLoaded(true)
            })
    }

    if (!isLoaded) return <DefaultLoader height={325} width={325}/>;
    return (
        <div>
            <div className="rooms_back">
                <p>{t(tableName)}</p>
                <Button
                    text={t("Add")}
                    onClick={_ => window.location.href = addEntityUrl}
                />
            </div>
            <div className="grid">
                {
                    data.map(element => {
                        return (
                            <div className="card text-center">
                                <div className="crd-body text-dark">
                                    <h2 className="card-title">{element.displayTitle}</h2>
                                    <hr/>
                                    {columns.map(column => {
                                        if (column.isHidden) return;
                                        const style = element[column.applyCustomStyle];
                                        return (
                                            <p className={`card-text text-secondary ${style}`}>
                                                {column.Header ? t(column.Header) + ': ' : ''}{column.insertBreak &&
                                                <br/>}{element[column.accessor]}
                                            </p>
                                        )
                                    })}
                                    {operations.map(operation => {
                                        return (<Button
                                            className={operation.className}
                                            text={t(operation.name)}
                                            onClick={() => {
                                                if (operation.name === 'Delete') {
                                                    handleDeleteOperation(operation.url,
                                                        element[operation.onClickPassParameter], operation.onClick)
                                                } else {
                                                    operation.onClick(element[operation.onClickPassParameter])
                                                }
                                            }}
                                        />)
                                    })}
                                </div>
                            </div>
                        )
                    })
                }
            </div>
        </div>
    )
}

function DataTableComponent({displayData, displayColumns, operations, tableName, addEntityUrl}) {
    const sortedData = displayData.sort((current, next) => {
        return current.id - next.id
    })
    const [data, setData] = useState(sortedData)
    const columns = React.useMemo(() => [...displayColumns], [])
    const translateColumns = columns.filter(column => column.toTranslate).map(column => column.accessor)
    const {t, i18n} = useTranslation();

    function deleteEntity(id) {
        let index = data.findIndex(entry => entry.id == id);
        data.splice(index, 1)
        setData(data.filter(entry => entry.id != id))
    }

    const translateTableData = () => {
        setData(data.map(entry => {
            translateColumns.forEach(column => {
                entry[column] = t(entry[column + "Translate"])
            })
            return entry
        }))
    }

    useEffect(() => {
        operations.forEach(operation => {
            if (operation.name === "Delete") {
                operation.onClick = deleteEntity
            }
        })
        if (_.isEmpty(translateColumns)) return;
        translateTableData()
        i18n.on("languageChanged", translateTableData)
    }, [])

    return (
        <Table columns={columns} data={data} operations={operations} tableName={tableName} addEntityUrl={addEntityUrl}/>
    )
}

export default withTranslation()(DataTableComponent);
