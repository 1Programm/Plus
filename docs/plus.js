import { ON_SERVER } from "./STATICS.js"


const SERVER_PREFIX = "/Plus/";

var ROOT_LOCATION;

if(ON_SERVER){
    console.log("-- STARTED ON SERVER --");
    ROOT_LOCATION = SERVER_PREFIX;
}
else {
    console.log("-- STARTED BY CLIENT --");
    ROOT_LOCATION = "/";
}

const PAGES_LOCATION = ROOT_LOCATION + "pages/";


var main;
var pageScript;
var pageHistorie = [];

var globals = {
    num_pages: 3
}

var elementPInfo;
var pinfo = {
    update(value){
        elementPInfo.innerHTML = evalExpression(value);
    },
    get(){
        return elementPInfo.innerHTML;
    }
}

window.init = function(){
    main = document.getElementById("main");
    elementPInfo = document.getElementById("page-info");

    moveLocation("start");
}

function getVar(obj, varName){
    let dot = varName.indexOf(".");

    if(dot == -1){
        return obj[varName];
    }
    else {
        var objName = varName.substring(0, dot);
        var restVarName = varName.substring(dot + 1);
        var nextObj = obj[objName];
        return getVar(nextObj, restVarName)
    }
}

function evalExpression(expr){
    var ret = "";

    let i = expr.indexOf("${");

    let before = 0;
    while(i != -1){
        ret += expr.substring(before, i);

        let closing = expr.indexOf("}", i);

        if(closing == -1) break;

        var val = expr.substring(i+2, closing);
        var pval = getVar(pageScript, val);

        ret += pval;

        before = closing + 1;
        i = expr.indexOf("${", before);
    }

    if(before < expr.length){
        ret += expr.substring(before);
    }

    return ret;
}

window.moveBack = function moveBack(){
    pageHistorie.pop();
    var nLoc = pageHistorie.pop();
    moveLocation(nLoc);
}

window.moveLocation = async function moveLocation(nLoc){
    if(!main) return;

    pageHistorie.push(nLoc);

    //Support Hashtags for section linking
    var hashPos = nLoc.lastIndexOf("#");
    var hashLink;
    if(hashPos != -1){
        hashLink = nLoc.substring(hashPos + 1);
        nLoc = nLoc.substring(0, hashPos);

        console.log("Move to page: '" + nLoc + "' - to section: '" + hashLink + "'");
    }
    else {
        console.log("Move to page: '" + nLoc + "'");
    }

    
    var rootLoc = PAGES_LOCATION + nLoc;

    var jsLoc = rootLoc + ".js";
    var obj = (await import(jsLoc)).default;
    
    pageScript = obj;
    window.pageScript = pageScript;

    if(obj.methods){
        for(const [key, val] of Object.entries(obj.methods)){
            pageScript[key] = val;
        }
    }

    if(obj.data){
        for(const [key, val] of Object.entries(obj.data)){
            pageScript[key] = val;
        }
    }


    //Setting globals
    if(obj.global){
        for(const [key, val] of Object.entries(obj.global)){
            globals[key] = val;
        }
    }

    pageScript.global = globals;


    //Page Info Listener
    if(pageScript.pinfo){
        let val = pageScript.pinfo;
        pageScript.pinfo = pinfo;
        pageScript.pinfo.update(val);
    }
    else {
        pageScript.pinfo = pinfo;
    }



    //Loading html
    var htmlLoc = rootLoc + ".html";
    var httpReq = new XMLHttpRequest();
    httpReq.open("GET", htmlLoc, false); 
    httpReq.send();

    var parser = new DOMParser();
    var htmlDoc = parser.parseFromString(httpReq.responseText, 'text/html');

    var bodyNode = htmlDoc.getElementsByTagName("body")[0];

    inspectNode(bodyNode);


    main.innerHTML = bodyNode.innerHTML;

    if(hashLink){
        document.getElementById(hashLink).scrollIntoView({behavior: 'smooth'});
    }
    





    //finished loading page
    if(pageScript.onfinish){
        pageScript.onfinish();
    }
}

function absolute(base, relative) {
    var stack = base.split("/"),
        parts = relative.split("/");
    //stack.pop(); // remove current file name (or empty string)
                 // (omit if "base" is the current folder without trailing slash)
    for (var i=0; i<parts.length; i++) {
        if (parts[i] == ".")
            continue;
        if (parts[i] == "..")
            stack.pop();
        else
            stack.push(parts[i]);
    }
    return stack.join("/");
}

function inspectNode(node){
    if(node.tagName == "LINK"){
        console.log("A link!", node, node.attributes);

        var _path = node.attributes["href"].value;
        console.log("_Path: ", _path);

        var path = absolute(ROOT_LOCATION, _path);
        console.log("Path: ", path);

        node.setAttribute("href", path)
    }

    var attribs = node.attributes;
    
    if(attribs){
        for(var i=0;i<attribs.length;i++){
            inspectAttribute(node, attribs[i]);
        }
    }


    var children = node.children;

    if(node.nodeType == 3){ //Is textNode
        
    }
    else {
        for(var i=0;i<children.length;i++){
            inspectNode(children[i]);
        }
    }
}

function inspectAttribute(node, attrib){
    var name = attrib.name;

    if(name.charAt(0) == "@"){
        var subName = name.substring(1);
        if(subName == "click"){
            node.setAttribute("onclick", parseValue(attrib.value));
        }
        else if(subName = "ref"){
            node.setAttribute("href", "#");
            node.setAttribute("onclick", "moveLocation('" + attrib.value + "')")
        }
    }
    else if(name == "onmouseover" 
    || name == "onmouseout") {
        attrib.value = parseValue(attrib.value);
    }

}

/*
 * Bsp: <button @click="sayHi()">Test</button>
 * Method "sayHi" only exists as a attribute inside 'pageScript'
 * So 'pageScript.' will be added before value
 * 
 * 
 * TODO: complex expressions and multiple function calls...
 * 
 */
function parseValue(value){
    return "pageScript." + value
}

window.handleKeyPress = function(key){
    var keyCode = event.code;

    if(key){
        keyCode = key
    }

    var nav = pageScript.nav;

    if(!nav) return;

    var loc = getLocFromNav(keyCode, nav);

    if(loc){
        if(loc == "back"){
            moveBack();
        }
        else {
            moveLocation(loc);
        }
    }
}

function getLocFromNav(code, nav){
    if(code == 'ArrowRight'){
        return nav.right;
    }
    else if(code == 'ArrowLeft'){
        return nav.left;
    }
    else if(code == 'ArrowUp'){
        return nav.up;
    }
    else if(code == 'ArrowDown'){
        return nav.down;
    }
    else {
        if(nav.page){
            return nav.page(code);
        }
    }
}
