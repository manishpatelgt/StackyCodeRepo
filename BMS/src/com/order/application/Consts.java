package com.order.application;

public final class Consts {

    public static final String WEBSERVICE_URL = "http://54.200.61.92:8080/ORDERING/rest/NeetaiAndroidServices/getUserVerified?login=string";
    public static final String WEBSERVICE_URL2 = "http://54.200.61.92:8080/ORDERING/rest/NeetaiAndroidServices/getCustomerByUserId";
    public static final String WEBSERVICE_URL3 = "http://54.200.61.92:8080/ORDERING/rest/NeetaiAndroidServices/getItemListByUserId";
    public static final String WEBSERVICE_URL4 = "http://54.200.61.92:8080/ORDERING/rest/NeetaiAndroidServices/getColourCode";
    public static final String WEBSERVICE_URL5 = "http://54.200.61.92:8080/ORDERING/rest/NeetaiAndroidServices/getOrderList";
    public static final String WEBSERVICE_URL6 = "http://54.200.61.92:8080/ORDERING/rest/NeetaiAndroidServices/InsertOrder";
    public static final String WEBSERVICE_URL7 = "http://54.200.61.92:8080/ORDERING/rest/NeetaiAndroidServices/getTodayOrder";
    public static final String WEBSERVICE_URL8="http://54.200.61.92:8080/ORDERING/rest/NeetaiAndroidServices/getOrderStage";
    public static final String WEBSERVICE_URL9="http://54.200.61.92:8080/ORDERING/rest/NeetaiAndroidServices/getOrderSearch";
    public static final String WEBSERVICE_URL10="http://54.200.61.92:8080/ORDERING/rest/NeetaiAndroidServices/getPendingOrder";
    public static final String WEBSERVICE_URL11="http://54.200.61.92:8080/ORDERING/rest/NeetaiAndroidServices/getTodayOrderSearch";
    public static final String WEBSERVICE_URL12="http://54.200.61.92:8080//ORDERING/rest/NeetaiAndroidServices/getitemdetailbyorderid";
    
    /**
     * Application-wide consts
     */
    public static final String APPLICATION_PACKAGE = "com.order.bms";
    public static final String APPLICATION_SERVICE_ACTION = APPLICATION_PACKAGE + ".action";
    public static final String APPLICATION_SERVICE_RESULT = APPLICATION_PACKAGE + ".serviceresult";
    public static final String APPLICATION_PATH = "/data/data/" + APPLICATION_PACKAGE;
    public static final String APPLICATION_DATABASES_PATH = APPLICATION_PATH + "/databases/";
  
    
}
