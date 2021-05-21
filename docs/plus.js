var main;
var pageScript;
var pageHistorie = [];

var globals = {
    num_pages: 4
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

function init(){
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

function moveBack(){
    pageHistorie.pop();
    var nLoc = pageHistorie.pop();
    moveLocation(nLoc);
}

async function moveLocation(nLoc){
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


    var rootLoc = "/Plus/pages/" + nLoc;

    var jsLoc = rootLoc + ".js";
    var obj = (await import(jsLoc)).default;
    
    pageScript = obj;

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

function inspectNode(node){
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

function handleKeyPress(key){
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
