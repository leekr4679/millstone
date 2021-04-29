package com.base.app.core.domain.base;

import com.base.app.core.code.Types.DataStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Transient;

public class CoreCrudModel {
    @Transient
    @JsonProperty("__deleted__")
    protected boolean __deleted__;
    @Transient
    @JsonProperty("__created__")
    protected boolean __created__;
    @Transient
    @JsonProperty("__modified__")
    protected boolean __modified__;

    @Transient
    public DataStatus getDataStatus() {
        if (this.__deleted__) {
            return DataStatus.DELETED;
        } else if (this.__created__) {
            return DataStatus.CREATED;
        } else {
            return this.__modified__ ? DataStatus.MODIFIED : DataStatus.ORIGIN;
        }
    }

    @Transient
    @JsonIgnore
    public boolean isDeleted() {
        return this.__deleted__;
    }

    @Transient
    @JsonIgnore
    public boolean isCreated() {
        return this.__created__;
    }

    @Transient
    @JsonIgnore
    public boolean isModified() {
        return this.__modified__;
    }

    public CoreCrudModel() {
    }

    public boolean is__deleted__() {
        return this.__deleted__;
    }

    public boolean is__created__() {
        return this.__created__;
    }

    public boolean is__modified__() {
        return this.__modified__;
    }

    public void set__deleted__(boolean __deleted__) {
        this.__deleted__ = __deleted__;
    }

    public void set__created__(boolean __created__) {
        this.__created__ = __created__;
    }

    public void set__modified__(boolean __modified__) {
        this.__modified__ = __modified__;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof CoreCrudModel)) {
            return false;
        } else {
            CoreCrudModel other = (CoreCrudModel)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.is__deleted__() != other.is__deleted__()) {
                return false;
            } else if (this.is__created__() != other.is__created__()) {
                return false;
            } else {
                return this.is__modified__() == other.is__modified__();
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof CoreCrudModel;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        result = result * 59 + (this.is__deleted__() ? 79 : 97);
        result = result * 59 + (this.is__created__() ? 79 : 97);
        result = result * 59 + (this.is__modified__() ? 79 : 97);
        return result;
    }

    public String toString() {
        return "CoreCrudModel(__deleted__=" + this.is__deleted__() + ", __created__=" + this.is__created__() + ", __modified__=" + this.is__modified__() + ")";
    }
}
