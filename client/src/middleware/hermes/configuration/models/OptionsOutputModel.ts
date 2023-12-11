/* tslint:disable */
/* eslint-disable */
/**
 * The Definition API
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { exists, mapValues } from '../runtime';
import type { OptionModel } from './OptionModel';
import {
    OptionModelFromJSON,
    OptionModelFromJSONTyped,
    OptionModelToJSON,
} from './OptionModel';

/**
 * The response object when dynamically resolving options.
 * @export
 * @interface OptionsOutputModel
 */
export interface OptionsOutputModel {
    /**
     * The error message.
     * @type {string}
     * @memberof OptionsOutputModel
     */
    errorMessage?: string;
    /**
     * 
     * @type {Array<OptionModel>}
     * @memberof OptionsOutputModel
     */
    options?: Array<OptionModel>;
}

/**
 * Check if a given object implements the OptionsOutputModel interface.
 */
export function instanceOfOptionsOutputModel(value: object): boolean {
    let isInstance = true;

    return isInstance;
}

export function OptionsOutputModelFromJSON(json: any): OptionsOutputModel {
    return OptionsOutputModelFromJSONTyped(json, false);
}

export function OptionsOutputModelFromJSONTyped(json: any, ignoreDiscriminator: boolean): OptionsOutputModel {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'errorMessage': !exists(json, 'errorMessage') ? undefined : json['errorMessage'],
        'options': !exists(json, 'options') ? undefined : ((json['options'] as Array<any>).map(OptionModelFromJSON)),
    };
}

export function OptionsOutputModelToJSON(value?: OptionsOutputModel | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'errorMessage': value.errorMessage,
        'options': value.options === undefined ? undefined : ((value.options as Array<any>).map(OptionModelToJSON)),
    };
}

