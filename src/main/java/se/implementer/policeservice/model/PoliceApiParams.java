package se.implementer.policeservice.model;

public enum PoliceApiParams {

    TYPE("type"),
    CITY("locationname"),
    DATE("DateTime");

    private final String paramType;

    PoliceApiParams(String paramType) {
        this.paramType = paramType;
    }

    public String getParamType() {
        return paramType;
    }
}
