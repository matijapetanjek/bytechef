/* tslint:disable */
/* eslint-disable */
/**
 * The Embedded Configuration API
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { mapValues } from '../runtime';
import type { ConnectedUserIntegrationInstanceModel } from './ConnectedUserIntegrationInstanceModel';
import {
    ConnectedUserIntegrationInstanceModelFromJSON,
    ConnectedUserIntegrationInstanceModelFromJSONTyped,
    ConnectedUserIntegrationInstanceModelToJSON,
} from './ConnectedUserIntegrationInstanceModel';

/**
 * 
 * @export
 * @interface ConnectedUserModel
 */
export interface ConnectedUserModel {
    /**
     * The created by.
     * @type {string}
     * @memberof ConnectedUserModel
     */
    readonly createdBy?: string;
    /**
     * The created date.
     * @type {Date}
     * @memberof ConnectedUserModel
     */
    readonly createdDate?: Date;
    /**
     * The email address.
     * @type {string}
     * @memberof ConnectedUserModel
     */
    email?: string;
    /**
     * If a connected user is enabled or not
     * @type {boolean}
     * @memberof ConnectedUserModel
     */
    enabled?: boolean;
    /**
     * The external reference code.
     * @type {string}
     * @memberof ConnectedUserModel
     */
    readonly externalReferenceCode: string;
    /**
     * The id of a connected user.
     * @type {number}
     * @memberof ConnectedUserModel
     */
    readonly id?: number;
    /**
     * 
     * @type {Array<ConnectedUserIntegrationInstanceModel>}
     * @memberof ConnectedUserModel
     */
    integrationInstances?: Array<ConnectedUserIntegrationInstanceModel>;
    /**
     * 
     * @type {{ [key: string]: any; }}
     * @memberof ConnectedUserModel
     */
    readonly metadata?: { [key: string]: any; };
    /**
     * The name of a connection.
     * @type {string}
     * @memberof ConnectedUserModel
     */
    name?: string;
    /**
     * The last modified by.
     * @type {string}
     * @memberof ConnectedUserModel
     */
    readonly lastModifiedBy?: string;
    /**
     * The last modified date.
     * @type {Date}
     * @memberof ConnectedUserModel
     */
    readonly lastModifiedDate?: Date;
    /**
     * 
     * @type {number}
     * @memberof ConnectedUserModel
     */
    version?: number;
}

/**
 * Check if a given object implements the ConnectedUserModel interface.
 */
export function instanceOfConnectedUserModel(value: object): boolean {
    if (!('externalReferenceCode' in value)) return false;
    return true;
}

export function ConnectedUserModelFromJSON(json: any): ConnectedUserModel {
    return ConnectedUserModelFromJSONTyped(json, false);
}

export function ConnectedUserModelFromJSONTyped(json: any, ignoreDiscriminator: boolean): ConnectedUserModel {
    if (json == null) {
        return json;
    }
    return {
        
        'createdBy': json['createdBy'] == null ? undefined : json['createdBy'],
        'createdDate': json['createdDate'] == null ? undefined : (new Date(json['createdDate'])),
        'email': json['email'] == null ? undefined : json['email'],
        'enabled': json['enabled'] == null ? undefined : json['enabled'],
        'externalReferenceCode': json['externalReferenceCode'],
        'id': json['id'] == null ? undefined : json['id'],
        'integrationInstances': json['integrationInstances'] == null ? undefined : ((json['integrationInstances'] as Array<any>).map(ConnectedUserIntegrationInstanceModelFromJSON)),
        'metadata': json['metadata'] == null ? undefined : json['metadata'],
        'name': json['name'] == null ? undefined : json['name'],
        'lastModifiedBy': json['lastModifiedBy'] == null ? undefined : json['lastModifiedBy'],
        'lastModifiedDate': json['lastModifiedDate'] == null ? undefined : (new Date(json['lastModifiedDate'])),
        'version': json['__version'] == null ? undefined : json['__version'],
    };
}

export function ConnectedUserModelToJSON(value?: ConnectedUserModel | null): any {
    if (value == null) {
        return value;
    }
    return {
        
        'email': value['email'],
        'enabled': value['enabled'],
        'integrationInstances': value['integrationInstances'] == null ? undefined : ((value['integrationInstances'] as Array<any>).map(ConnectedUserIntegrationInstanceModelToJSON)),
        'name': value['name'],
        '__version': value['version'],
    };
}
