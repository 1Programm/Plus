import { Assure } from "./OUtils.js";

export class App{
    constructor(obj, elLink){
        const el = document.getElementById(elLink);
        Assure(el, "Could not find element!", elLink);

        this.idCounter = 0;
        this.generateId = function(){
            return "_observable_" + (this.idCounter++);
        }

        el._observable_app = this;

        BindToObj(obj, el, this);
    }
};

export function BindToObj(script, el, obj, name){
    Assure(el, "Element is null!");

    const data = script.data;
    const onload = script.onload;
    const componentsFolder = script.componentsFolder;
    const components = script.components;

    delete script.data;
    delete script.onload;
    delete script.componentsFolder;
    delete script.components;

    if(name){
        obj.name = name;
    }

    for(let key in data){
        script[key] = data[key];
    }

    obj.el = el;

    const exListeners = {};
    obj.listenFor = {
        expr(expression, node, index){
            var exListenerList = exListeners[expression];
            if(!exListenerList){
                exListenerList = [];
                exListeners[expression] = exListenerList;
            }

            const exListener = {
                node,
                index
            };

            exListenerList.push(exListener);
        },
        events(eventName, el, varname){
            el.addEventListener(eventName, () => {
                obj.script[varname](); //run the function
            });
        },
        observeEquals(varname, testValue, receiver){
            MakeObservable(obj.script, (oldVal, newVal, varpath) => {
                if(varpath == varname){
                    if(newVal == testValue){
                        receiver(true);
                    }
                    else {
                        receiver(false);
                    }
                }
            });

            const val = obj.script[varname];
            if(val === undefined){
                console.error("No variable [" + varname + "] found!");
            }
            else if(val != testValue){
                receiver(false);
            }
        }
    };

    obj.script = MakeObservable(script, (oldVal, newVal, varpath) => {
        const exListenerList = exListeners[varpath];

        if(exListenerList){
            for(let i=0;i<exListenerList.length;i++){
                const exListener = exListenerList[i];
                const node = exListener.node;
                const index = exListener.index;

                node.exprVal[index] = newVal;

                var text = "";
                for(let i=0;i<node.exprCon.length;i++){
                    text += node.exprCon[i];

                    if(i + 1 < node.exprCon.length){
                        text += node.exprVal[i];
                    }
                }

                node.setExpr(text);
            }
        }
    });

    registerListenersInDom(obj.el, obj.listenFor);
    obj.script._observable_proxyNotify();
    obj.script._observable_app = obj;

    for(let key in components){
        let path = componentsFolder;
        if(!path){
            path = "";
        }
        else if(!path.endsWith("/")){
            path += "/";
        }

        path += components[key];
        loadComponent(path, key, el);
    }

    if(onload){
        obj.script.onload = onload;
        obj.script.onload();
        delete obj.script.onload;
    }
}

function loadComponent(componentPath, name, el){
    Assure(name, "No name defined!", componentPath);

    if(!componentPath.endsWith(".comp")){
        componentPath = componentPath + ".comp";
    }

    const request = new XMLHttpRequest();
    request.open("GET", componentPath, false); 
    request.send();

    const parser = new DOMParser();
    const htmlDoc = parser.parseFromString(request.responseText, 'text/html');

    
    const templateNode = htmlDoc.getElementsByTagName("template")[0];
    Assure(templateNode, "No template defined", componentPath);


    const scriptNode = htmlDoc.getElementsByTagName("script")[0];
    var script;

    if(scriptNode){
        script = parseScript(scriptNode.innerHTML);
        Assure(script, "Component must export data!", componentPath);
    }
    else {
        script = {};
    }

    const styleNode = htmlDoc.getElementsByTagName("style")[0];
    if(styleNode){
        const newId = getAppFromEl(el).generateId();
        templateNode._observable_elId = newId;
        scopeStyleForComponent(styleNode, newId);
        console.log(styleNode);
    }






    const theApp = {};

    class CustomComponent extends HTMLElement {
        constructor(){
            super();
            this.innerHTML = templateNode.innerHTML;

            if(styleNode){
                this.appendChild(styleNode);
                this.id = templateNode._observable_elId;
            }

            BindToObj(script, this, theApp);
        }
    }


    var definedComponent = customElements.get(name);
    if(!definedComponent){
        customElements.define(name, CustomComponent);
    }

    return theApp;
}

function scopeStyleForComponent(styleNode, newId){
    var text = styleNode.innerHTML.trim();
    var ntext = "";

    var index = 0;
    var nextClosing = text.indexOf("}");
    while(nextClosing != -1){
        var item = text.substring(index, nextClosing + 1);
        item = item.trim();

        console.log("Item: " + item);

        ntext += "#" + newId + " " + item;

        index = nextClosing + 1;
        nextClosing = text.indexOf("}", index);
    }

    if(index < text.length){
        ntext += "#" + newId + " " + text.substring(0);
    }

    styleNode.innerHTML = ntext;
}

function parseScript(script){
    script = script.replace("export default", "return");
    script = script.replace("export", "return");

    var parsedFunction = new Function(script);
    return parsedFunction();
}

function getAppFromEl(el){
    var app = el._observable_app;
    if(!app) {
        if(!el.parentElement){
            return null;
        }
        app = getAppFromEl(el.parentElement);
    }

    return app;
}


function registerListenersInDom(el, listenFor){
    const attribs = el.attributes;
    for(let i=0;i<attribs.length;i++){
        const attrib = attribs[i];
        const aname = attrib.name;
        const aval = attrib.value;

        if(aname.charAt(0) == "@"){
            if(aname == "@click"){
                listenFor.events("click", el, aval);
            }
            else if(aname == "@if"){
                listenFor.observeEquals(aval, true, (equals) => {
                    if(equals){
                        el.style.visibility='visible';
                        el.style.maxHeight = "";
                        el.style.marginBlockStart = "";
                        el.style.marginBlockEnd = "";
                    }
                    else {
                        el.style.visibility='hidden';
                        el.style.maxHeight = "0px";
                        el.style.marginBlockStart = "0";
                        el.style.marginBlockEnd = "0";
                    }
                });

                // const script = getAppFromEl(el).script;
                // var scriptVar = script[aval];
                // console.log(el.style);

                // if(scriptVar){
                //     MakeObservable(script, (oldVal, newVal, varpath) => {
                //         if(varpath == aval){
                //             if(oldVal == true && newVal == false){
                //                 el.style.visibility='hidden';
                //                 el.style.maxHeight = "0px";
                //                 el.style.marginBlockStart = "0";
                //                 el.style.marginBlockEnd = "0";
                //             }
                //             else if(oldVal == false && newVal == true){
                //                 el.style.visibility='visible';
                //                 el.style.maxHeight = "";
                //                 el.style.marginBlockStart = "";
                //                 el.style.marginBlockEnd = "";
                //             }
                //         }
                //     });

                //     if(scriptVar == false){
                //         el.style.visibility='hidden';
                //         el.style.maxHeight = "0px";
                //         el.style.marginBlockStart = "0";
                //         el.style.marginBlockEnd = "0";
                //     }
                // }
                // else {
                //     console.error("No data object [" + aval + "] found!");
                // }
            }
            else {
                const _aname = aname.substring(1);
                el.setAttribute(_aname, aval);
                const _attrib = el.attributes[_aname];

                checkContentAndRegisterListenersInAttrib(_attrib, listenFor);
            }
        }

        //TODO
    }

    for(let i=0;i<el.childNodes.length;i++){
        var child = el.childNodes[i];
        
        const type = child.nodeType;
        if(type == 3){
            var computedContent = checkContentAndRegisterListenersInNode(child, listenFor);
            child.textContent = computedContent;
        }
        else if(type != 8){
            registerListenersInDom(el.childNodes[i], listenFor);
        } 
    }
}

function checkContentAndRegisterListenersInNode(node, listenFor){
    var content = node.textContent;
    var reactiveContent = [];
    var reactiveValues = [];

    var i = content.indexOf("{{");
    var before = 0;

    while(i != -1){
        var pre = content.substring(before, i);
        reactiveContent.push(pre);

        var closing = content.indexOf("}}", i);
        if(closing == -1) break;

        var expr = content.substring(i + 2, closing);
        expr = evaluateExpression(expr);
        reactiveValues.push(expr);

        before = closing + 2;
        i = content.indexOf("{{", before);
    }

    if(before <= content.length){
        var rest = content.substring(before);
        reactiveContent.push(rest);
    }

    if(reactiveValues.length > 0){
        node.exprVal = reactiveValues;
        node.exprCon = reactiveContent;
        node.setExpr = function(text){
            this.textContent = text;
        }


        var calcContent = "";
        for(let i in reactiveContent){
            calcContent += reactiveContent[i];
            if(i < reactiveValues.length){
                listenFor.expr(reactiveValues[i], node, i);
            }
        }

        return calcContent;
    }
    else {
        return reactiveContent[0];
    }
}

function checkContentAndRegisterListenersInAttrib(attrib, listenFor){
    var content = attrib.value;
    var reactiveContent = [];
    var reactiveValues = [];

    var i = content.indexOf("{{");
    var before = 0;

    while(i != -1){
        var pre = content.substring(before, i);
        reactiveContent.push(pre);

        var closing = content.indexOf("}}", i);
        if(closing == -1) break;

        var expr = content.substring(i + 2, closing);
        expr = evaluateExpression(expr);
        reactiveValues.push(expr);

        before = closing + 2;
        i = content.indexOf("{{", before);
    }

    if(before <= content.length){
        var rest = content.substring(before);
        reactiveContent.push(rest);
    }

    if(reactiveValues.length > 0){
        attrib.exprVal = reactiveValues;
        attrib.exprCon = reactiveContent;
        attrib.setExpr = function(text){
            this.value = text;
        }

        var calcContent = "";
        for(let i in reactiveContent){
            calcContent += reactiveContent[i];
            if(i < reactiveValues.length){
                listenFor.expr(reactiveValues[i], attrib, i);
            }
        }

        return calcContent;
    }
    else {
        return reactiveContent[0];
    }
}


function evaluateExpression(expr){
    return expr.trim();
}

// --- OBSERVABLE --- //

function OLog(log, ...args){
    if(true){
        console.log(log, ...args);
    }
}

function ObserveExpression(obj, listeners, expression){

}

export function MakeObservable(obj, listeners, varpath){
    OLog("Observable Varpath: ", varpath);

    if(obj._observable_isProxy){
        OLog("Is Proxy: ", obj);
        const proxyListeners = obj._observable_proxyListeners;

        if(!listeners) {
            listeners = [];
        }
        proxyListeners.push(listeners);

        return obj;
    }
    else if(typeof obj === 'string'){
        OLog("Is String: ", obj);
        return obj;
    }
    else if(typeof obj === 'number'){
        OLog("Is Number: ", obj);
        return obj;
    }
    else if(typeof obj === 'boolean'){
        OLog("Is Boolean: ", obj);
        return obj;
    }
    else if(typeof obj === 'function'){
        OLog("Is Function: ", obj);
        return obj;
    }
    else {
        OLog("Is Object: ", obj);

        if(!listeners || listeners == null){
            listeners = [];
        }
        else {
            if(!Array.isArray(listeners)){
                listeners = [listeners];
            }
        }

        const isArray = Array.isArray(obj);

        if(!isArray){
            var nObj = {};

            for(let p in obj){
                var nvarpath = (varpath) ? varpath + "." + p : "" + p;
                var oldVal = obj[p];
                var nProxy = MakeObservable(oldVal, listeners, nvarpath);
                nObj[p] = nProxy;
            }

            obj = nObj;
        }
        

        const proxyListeners = listeners;
        const proxyVarPath = (varpath) ? varpath : "";


        const proxyHandler = {
            get: function(obj, prop, receiver){
                if(prop == "_observable_isProxy"){
                    return true;
                }
                else if(prop == "_observable_proxyListeners"){
                    return proxyListeners;
                }
                else if(prop == "_observable_proxyNotify"){
                    return function(){
                        for(let key in obj){
                            var val = obj[key];

                            for(let i in proxyListeners){
                                proxyListeners[i](val, val, proxyVarPath + key);
                            }
                        }
                    };
                }
                else if(isArray && prop == "length"){
                    return obj.length;
                }

                return obj[prop];
            },
            set: function(obj, prop, val){
                const oldVal = obj[prop];

                if(oldVal != val){
                    obj[prop] = val;
                    for(let i in proxyListeners){
                        proxyListeners[i](oldVal, val, proxyVarPath + prop);
                    }
                }
                return true;
            }
        };

        return new Proxy(obj, proxyHandler);
    }
}
