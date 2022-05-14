import React from 'react'
import './App.css';
import {observer} from 'mobx-react'
import {
    Route,
    Switch,
    BrowserRouter,
    Redirect
} from "react-router-dom"
import SignIn from './View/Components/navigation/auth/Login'
import SignUp from './View/Components/navigation/auth/SignUp'
import Profile from './View/Components/navigation/employee/Profile'
import {withTranslation} from 'react-i18next'
import Edit from './View/Components/navigation/employee/Edit';
import AddMeetingRoom from './View/Components/navigation/meeting-room/AddMeetingRoom'
import EditMeetingRoom from './View/Components/navigation/meeting-room/EditMeetingRoom';
import AddOfficeBuilding from "./View/Components/navigation/office-building/AddOfficeBuilding";
import EditOfficeBuilding from "./View/Components/navigation/office-building/EditOfficeBuilding";
import OfficeBuildingInfoPage from "./View/Components/navigation/office-building/OfficeBuildingInfoPage";

class App extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            value: "en"
        }
    }

    render() {
        return (
            <div className="App">
                <BrowserRouter>
                    <Switch>
                        <Route path='/login' component={SignIn}/>
                        <Route path='/signup' component={SignUp}/>

                        <Route path='/profile' component={Profile}/>
                        <Route path='/edit' component={Edit}/>

                        <Route path='/add-office-building' component={AddOfficeBuilding}/>
                        <Route path='/edit-office-building' component={EditOfficeBuilding}/>

                        <Route path='/add-meeting-room' component={AddMeetingRoom}/>
                        <Route path='/edit-meeting-room' component={EditMeetingRoom}/>
                        <Route path='/office-building-info' component={OfficeBuildingInfoPage}/>

                        <Route path={'/bookings/:getBy/:id'} children={AddMeetingRoom}/>

                        <Route path={'/create-booking'} component={AddMeetingRoom}/>
                        <Redirect from='/' to='/login'/>
                    </Switch>
                </BrowserRouter>
            </div>
        )
    }
}

export default withTranslation()(observer(App));
