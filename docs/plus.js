var main;
var pageScript;
var pageHistorie = [];

function init(){
    main = document.getElementById("main");
    moveLocation("start");
}

function moveBack(){
    pageHistorie.pop();
    var nLoc = pageHistorie.pop();
    moveLocation(nLoc);
}

async function moveLocation(nLoc){
    console.log("Move to page: " + nLoc);
    pageHistorie.push(nLoc);

    var rootLoc = "/pages/" + nLoc;

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


    //Loading css
    // var head = document.getElementsByTagName("head")[0];
    // var nLink = document.createElement("link");
    // nLink.id = "pageStyle";
    // nLink.rel = "stylesheet";
    // nLink.href = "styles/" + nLoc + ".css";
    // head.appendChild(nLink);



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
        if(subName == "onmouseover"){
            
        }
    }
    else if(name == "onmouseover" 
    || name == "onmouseout") {
        attrib.value = "pageScript." + attrib.value;
    }

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