import React, {useState} from 'react'
import {useTranslation, withTranslation} from 'react-i18next'
import * as Constants from "../util/Constants";
import DefaultLoader from "../ui/Loader";
import {authInstance} from "../util/ApiUtil";
import {OFFICE_BUILDINGS} from "../util/Constants";
import {setProfileShownTable, setToken, setTokenValues} from "../util/LocalStorageUtils";
import _ from "lodash";
import {useForm} from "react-hook-form";
import Button from "../ui/Button";

function LoginForm() {
    const [isLoaded, setIsLoaded] = useState(true);
    const [error, setError] = useState("");
    const {register, handleSubmit, formState: {errors}} = useForm();

    const handleResult = result => {
        const token = result.data.token;
        setToken(token);
        setTokenValues(token);
        setProfileShownTable(OFFICE_BUILDINGS);
        window.location.href = './profile';
    }

    const handleError = error => {
        const data = error.response.data
        if (data.trace.includes('account is locked')) {
            setError("accIsLocked")
        } else {
            setError("checkCred")
        }
        setIsLoaded(true)
    }

    const onSubmit = data => {
        if (!_.isEmpty(errors)) return;
        setIsLoaded(false);
        authInstance.post(`/auth/login`, data).then(handleResult).catch(handleError);
    }

    const {t} = useTranslation();
    const inputClass = Constants.INPUT_STYLE_CLASSES;
    const errorInputClass = inputClass + " w3-border-red";

    if (!isLoaded) return <DefaultLoader height={400} width={425} isCentered={false}/>;
    return (
        <form className="signInForm" onSubmit={handleSubmit(onSubmit)}>
            <div className='signInContainer'>
                <h1 className={"w3-center"}>{t("Login")}</h1>

                {error && <p className="w3-center text-danger">{t(error)}</p>}

                <input type={"text"} className={errors["email"] ? errorInputClass : inputClass}
                       placeholder={t('Email')}
                       {...register("email", {
                           required: true,
                           pattern: /^([a-z0-9_-]+.)*[a-z0-9_-]+@[a-z0-9_-]+(.[a-z0-9_-]+)*.[a-z]{2,6}$/
                       })} />
                {errors["email"] && <><small className="w3-text-red">{t("EEmail")}</small><br/></>}

                <input type={"password"} className={errors["password"] ? errorInputClass : inputClass}
                       placeholder={t('Password')}
                       {...register("password", {
                           required: true,
                           pattern: /^.{8,20}$/
                       })} />
                {errors["password"] && <><small className="w3-text-red">{t("EPass")}</small><br/></>}

                <Button text={t('Signin')} type="submit"/>
            </div>
        </form>
    );
}

export default withTranslation()(LoginForm);
