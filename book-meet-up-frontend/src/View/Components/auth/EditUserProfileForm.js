import React from 'react'
import Input from '../ui/Input'
import Button from '../ui/Button'
import {withTranslation} from 'react-i18next'
import * as Constants from "../util/Constants";
import {ADMIN, EMPLOYEE} from "../util/Constants";
import axios from "../util/ApiUtil";
import DefaultLoader from "../ui/Loader";
import {
    getCurrentEmployee,
    getCurrentUserEmail,
    getCurrentUserRole,
    getEditUserEmail,
    getEditUserRole,
    setCurrentEmployee
} from "../util/LocalStorageUtils";

const userRole = getCurrentUserRole();
const userEmail = getCurrentUserEmail();

class EditUserProfileForm extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            firstName: '',
            lastName: '',
            email: '',
            password: '',
            confirmPass: '',
            phone: '',
            flag: 1,
            buttonDisabled: false,
            isLoading: false
        }
    }

    setInputValue(property, val) {
        this.setState({
            [property]: val
        })
    }

    turnOffLoader() {
        this.setState({
            buttonDisabled: false,
            isLoaded: true
        })
    }

    componentDidMount() {
        if (userRole === EMPLOYEE) {
            if (this.checkCachedEmployee()) return;
            this.getData(`/employees/${userEmail}`);
        } else if (userRole === ADMIN) {
            if (getEditUserRole() === EMPLOYEE) {
                this.getData(`/employees/${getEditUserEmail()}`);
            }
        }
    }

    checkCachedEmployee() {
        const cached = getCurrentEmployee();
        if (cached != null) {
            this.setState({
                isLoaded: true,
                firstName: cached.firstName,
                lastName: cached.lastName,
                email: cached.email,
                phone: cached.phoneNumber,
                id: cached.id
            })
            return true
        }
        return false
    }

    getData(resUrl) {
        axios.get(resUrl).then(result => {
            result = result.data
            this.setState({
                isLoaded: true,
                firstName: result.firstName,
                lastName: result.lastName,
                email: result.email,
                phone: result.phoneNumber,
                id: result.id
            });
        }).catch(error => {
            this.setState({
                isLoaded: true,
                error
            });
        })
    }

    checkEmail(email) {
        let regEmail = new RegExp('^([a-z0-9_-]+.)*[a-z0-9_-]+@[a-z0-9_-]+(.[a-z0-9_-]+)*.[a-z]{2,6}$');
        if (!regEmail.test(email)) {
            this.setState({flag: 2});
            return false
        }
        return true
    }

    checkName(name) {
        let regName = new RegExp('^([??-??????-????0-9]+)|([A-Za-z0-9]+)$');
        if (!regName.test(name)) {
            this.setState({flag: 4});
            return false
        }
        return true
    }

    checkPhone(phone) {
        let regPhone = new RegExp('^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-s./0-9]*$');
        if (!regPhone.test(phone)) {
            this.setState({flag: 5});
            return false
        }
        return true
    }

    checkPass(password) {
        if (password.length < 8) {
            this.setState({flag: 11});
            return false
        }
        return true
    }

    checkPasswords(password, confirmPassword) {
        if (password !== confirmPassword) {
            this.setState({flag: 12});
            return false
        }
        return true
    }

    checkCred() {
        if (!this.checkName(this.state.firstName)) {
            return
        }
        if (!this.checkName(this.state.lastName)) {
            return
        }
        if (!this.checkEmail(this.state.email)) {
            return
        }
        if (!this.checkPhone(this.state.phone)) {
            return
        }
        if (this.state.password && !this.checkPass(this.state.password)) {
            return
        }
        if (this.state.confirmPass && !this.checkPasswords(this.state.password, this.state.confirmPass)) {
            return
        }

        this.setState({
            buttonDisabled: true,
            isLoaded: false
        })

        if (userRole === EMPLOYEE || (userRole === ADMIN && getEditUserRole() === EMPLOYEE)) {
            this.editUserProfile(`/employees`);
        }
    }

    async editUserProfile(resUrl) {
        try {
            const role = userRole !== "ADMIN" ? userRole : getEditUserRole();

            let requestBody = {
                firstName: this.state.firstName,
                lastName: this.state.lastName,
                email: this.state.email,
                id: this.state.id,
                phoneNumber: this.state.phone,
                role
            }

            if (this.state.password) {
                requestBody.password = this.state.password;
            }

            let res = await axios.put(resUrl, requestBody)
            let result = await res.data
            if (result && result.id !== null) {
                setCurrentEmployee({
                    firstName: this.state.firstName,
                    lastName: this.state.lastName,
                    email: this.state.email,
                    id: this.state.id,
                    phoneNumber: this.state.phone,
                    role: role
                });
                window.location.href = './profile';
            }
        } catch (e) {
            this.turnOffLoader()
            this.setState({flag: 13});
        }
    }

    render() {
        const {t} = this.props
        const inputClass = Constants.INPUT_STYLE_CLASSES;
        if (!this.state.isLoaded) {
            return <DefaultLoader height={425} width={425} isCentered={false}/>
        }
        return (
            <div className="signUpForm entity-form-border">
                <div className="signUpContainer">
                    <h1 className="w3-center">{t('Edit')}</h1>
                    <div className="sized-font w3-center w3-text-red">
                        {this.state.flag === 2 && <span>{t("EEmail")}</span>}
                        {this.state.flag === 4 && <p>{t("EName")}</p>}
                        {this.state.flag === 5 && <p>{t("EPhone")}</p>}
                        {this.state.flag === 10 && <p>{t("eExist")}</p>}
                        {this.state.flag === 11 && <p>{t("EPass")}</p>}
                        {this.state.flag === 12 && <p>{t("EConfirmPass")}</p>}
                        {this.state.flag === 13 && <p>{t("ErrorResponse")}</p>}
                    </div>

                    <Input
                        className={this.state.flag === 4 ? inputClass + " w3-border-red" : inputClass}
                        type='text'
                        placeholder={t('FirstName')}
                        value={this.state.firstName ? this.state.firstName : ''}
                        onChange={(val) => this.setInputValue('firstName', val)}
                    />

                    <Input
                        className={this.state.flag === 4 ? inputClass + " w3-border-red" : inputClass}
                        type='text'
                        placeholder={t('LastName')}
                        value={this.state.lastName ? this.state.lastName : ''}
                        onChange={(val) => this.setInputValue('lastName', val)}
                    />

                    <Input
                        disabled="true"
                        className={this.state.flag === 2 ? inputClass + " w3-border-red" : inputClass}
                        type='text'
                        placeholder={t('Email')}
                        value={this.state.email ? this.state.email : ''}
                        onChange={(val) => this.setInputValue('email', val)}
                    />

                    <Input
                        className={this.state.flag === 5 ? inputClass + " w3-border-red" : inputClass}
                        type='text'
                        placeholder={t('Phone')}
                        value={this.state.phone ? this.state.phone : ''}
                        onChange={(val) => this.setInputValue('phone', val)}
                    />

                    <Input
                        className={(this.state.flag === 11 || this.state.flag === 12) ? inputClass + " w3-border-red" : inputClass}
                        type='password'
                        placeholder={t('Password')}
                        value={this.state.password ? this.state.password : ''}
                        onChange={(val) => this.setInputValue('password', val)}
                    />

                    <Input
                        className={this.state.flag === 12 ? inputClass + " w3-border-red" : inputClass}
                        type='password'
                        placeholder={t('ConfirmPassword')}
                        value={this.state.confirmPass ? this.state.confirmPass : ''}
                        onChange={(val) => this.setInputValue('confirmPass', val)}
                    />

                    <Button
                        className="btn"
                        text={t('Edit')}
                        disabled={this.state.buttonDisabled}
                        onClick={() => this.checkCred()}
                    />
                </div>
            </div>
        )
    }
}

export default withTranslation()(EditUserProfileForm);
