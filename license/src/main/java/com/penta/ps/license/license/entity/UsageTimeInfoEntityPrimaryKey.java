package com.penta.ps.license.license.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class UsageTimeInfoEntityPrimaryKey implements Serializable {
    private String serialNo;
    private LocalDate date;

    @Override
    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }
        if(obj == null || getClass() != obj.getClass() ){
            return false;
        }
        UsageTimeInfoEntityPrimaryKey other = (UsageTimeInfoEntityPrimaryKey) obj;
        return serialNo == other.serialNo && Objects.equals(date, other.date);
    }

    @Override
    public int hashCode(){
        return Objects.hash(serialNo, date);
    }
}
