package com.programm.plus.tags;

import com.programm.plus.core.component.Component;
import lombok.Getter;

@Getter
class RequiredClassInfo {

    private Class<? extends Component> requiredComponentClass;
    private Object[] defaultConstructorParams;

    public RequiredClassInfo(Class<? extends Component> requiredComponentClass, Object... defaultConstructorParams){
        this.requiredComponentClass = requiredComponentClass;
        this.defaultConstructorParams = defaultConstructorParams;
    }

    public int numParams(){
        return defaultConstructorParams == null ? 0 : defaultConstructorParams.length;
    }

}
