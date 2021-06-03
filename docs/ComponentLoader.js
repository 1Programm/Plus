import * as Utils from "./Utils.js"

function assure(statement, message, path){
    if(!statement) throw new Error(message + " in component [" + path + "]!");
}

export async function load(componentPath, componentName){
    if(!componentPath.endsWith(".comp")){
        componentPath = componentPath + ".comp";
    }

    console.log("Loading Component: " + componentPath);

    var request = new XMLHttpRequest();
    request.open("GET", componentPath, false); 
    request.send();

    var parser = new DOMParser();
    var htmlDoc = parser.parseFromString(request.responseText, 'text/html');

    
    var templateNode = htmlDoc.getElementsByTagName("template")[0];
    assure(templateNode, "No template defined", componentPath);


    var scriptNode = htmlDoc.getElementsByTagName("script")[0];
    assure(scriptNode, "No script defined", componentPath);

    var script = parseScript(scriptNode.innerHTML);
    console.log("Script: ", script);

    var reactiveScript = Utils.Reactive(script);

    class CustomComponent extends HTMLElement {
        constructor(){
            super();
            this.innerHTML = templateNode.innerHTML;

            visitNode(this, reactiveScript);
        }
    }

    if(!componentName){ 
        componentName = reactiveScript.name;
    }

    console.log(componentName);

    customElements.define(componentName, CustomComponent);
}

function parseScript(script){
    script = script.replace("export default", "return");
    script = script.replace("export", "return");

    var parsedFunction = new Function(script);
    return parsedFunction();
}

function visitNode(node, script){
    console.log("Checked: ", node.tagName);

    var children = node.children;
    if(children.length == 0){
        checkTextNode(node, script);
    }
    else {
        for(var i=0;i<children.length;i++){
            visitNode(children[i], script);
        }
    }
}

function checkTextNode(node, script){
    //console.log("Text: " + node.innerHTML);

    var content = node.innerHTML;
    var reactiveContent = "";
    var reactiveValues = [];

    var i = content.indexOf("{{");
    var before = 0;

    while(i != -1){
        var pre = content.substring(before, i);
        reactiveContent += pre;

        var closing = content.indexOf("}}", i);
        if(closing == -1) break;

        var expr = content.substring(i + 2, closing);
        expr = evaluateExpression(expr);
        reactiveValues.push(expr);

        reactiveContent += "%s";

        before = closing + 2;
        i = content.indexOf("{{", before);
    }

    if(before <= content.length){
        var rest = content.substring(before);
        reactiveContent += rest;
    }

    if(reactiveValues.length > 0){
        console.log("Reactivity: ", reactiveContent);

        for(var o=0;o<reactiveValues.length;o++){
            var reactiveVal = reactiveValues[o];

            script.data.observe(reactiveVal, (nvar) => {
                console.log("OBSERVE: " + nvar);
            });
        }

        for(var o=0;o<reactiveValues.length;o++){
            var reactiveVal = reactiveValues[o];

            script.notify(reactiveValues[o]);
        }
    }
    else {
        console.log("No reactivity: ", reactiveContent);
        node.innerHTML = reactiveContent;
    }
}

function evaluateExpression(expr){
    return "data." + expr.trim();
}

function format(str) {
    var args = [].slice.call(arguments, 1);
    var i = 0;

    return str.replace("%s", () => args[i++]);
}