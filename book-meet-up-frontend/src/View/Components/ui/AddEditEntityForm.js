import React, {useState} from 'react'
import {useForm} from 'react-hook-form';
import {useTranslation, withTranslation} from 'react-i18next'
import * as Constants from "../util/Constants";
import _ from "lodash";
import DefaultLoader from "./Loader";
import {removeItem} from "../util/LocalStorageUtils";

function AddEditEntityForm({requestPayload, fields, formName}) {
    const {t} = useTranslation();
    const inputClass = Constants.INPUT_STYLE_CLASSES;
    const errorInputClass = inputClass + " border-red";
    const requestBody = requestPayload.body;

    const [isLoaded, setIsLoaded] = useState(true);
    const [isErrorResponse, setIsErrorResponse] = useState(false);

    const {register, handleSubmit, formState: {errors}} = useForm();
    const onSubmit = data => {
        if (!_.isEmpty(errors)) return;
        setIsLoaded(false)

        requestPayload.function(requestPayload.url, data)
            .then(result => {
                    if (result.data) {
                        removeItem(requestPayload.entityId)
                        window.location.href = requestPayload.redirectUrl;
                    }
                }
            )
            .catch(e => {
                setIsLoaded(true);
                setIsErrorResponse(true);
            })
    };

    if (!isLoaded) {
        return <DefaultLoader height={400} width={425} isCentered={false}/>
    }
    return (
        <div className="signUpForm entity-form-border">
            <form className="signUpContainer" onSubmit={handleSubmit(onSubmit)}>
                <h1 className="w3-center">{t(formName.header)}</h1>
                <input type="number" hidden={true} value={requestBody.id} {...register("id", {valueAsNumber: true})}/>
                <div className="sized-font w3-center w3-text-red">
                    {isErrorResponse && <p>{t("Error")}</p>}
                </div>
                {
                    fields.map(field =>
                        <div>
                            <input type={field.inputType} className={errors[field.name] ? errorInputClass : inputClass}
                                   defaultValue={requestBody[field.name]} placeholder={t(field.label)}
                                   {...register(field.name, {
                                       required: true,
                                       pattern: field.pattern
                                   })} />
                            {errors[field.name] && <small style={{fontSize: "17px", position: "static"}}
                                                          className="w3-text-red">{t(field.error)}</small>}
                        </div>
                    )
                }
                <br/>
                <input value={t(formName.submitButton)} className={"btn"} type="submit"/>
            </form>
        </div>
    );
}

export default withTranslation()(AddEditEntityForm)
