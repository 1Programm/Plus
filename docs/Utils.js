
function Reactive(dataObj) {
    console.log("Reactive: ", dataObj);
    let signals = {}
  

    if(typeof dataObj != "object"){
        console.log("Not an obj");
        let val = dataObj;
        dataObj = {value: val};
        Object.defineProperty(dataObj, "value", {
            get () {
                return val;
            },
            set (newVal) {
                val = newVal;
                this.notify("value");
            }
        })
    }
    else {
        observeData(dataObj)
    }
  
    
    // Besides the reactive data object, we also want to return and thus expose the observe and notify functions.
    dataObj["observe"] = observe;
    dataObj["notify"] = notify;

    //statics
    //dataObj["_this"] = dataObj;

    return dataObj;


    function observe (property, signalHandler) {
      if(!signals[property]) signals[property] = []
  
      signals[property].push(signalHandler)
    }
  
    function notify (signal) {
        if(!signals[signal] || signals[signal].length < 1) return
        
        signals[signal].forEach((signalHandler) => signalHandler(this[signal]))
    }
  
    function makeReactive (obj, key) {
      let val = obj[key]
      val = Reactive(val);
  
      Object.defineProperty(obj, key, {
        get () {
          return val
        },
        set (newVal) {
          val = newVal
          this.notify(key)
        }
      })
    }
  
    function observeData (obj) {
        for (let key in obj) {
            if (obj.hasOwnProperty(key)) {
                makeReactive(obj, key)
            }
        }
    }
}

export let Reactive_ = Reactive;


class Observable{
    constructor(value){
        this.value = value;
        this.signals = [];
    }
    observe(signalHandler){
        this.signals.push(signalHandler);
    }
    notify(){
        for(let handler in this.signals){
            handler(this.value);
        }
    }
    get(){
        return this.value;
    }
    set(nVal){
        this.value = nVal;
        this.notify();
    }
}

export function Observe(value){
    if(typeof value != "object"){
        return new Observable(value);
    }
    else {

    }
}

// {
//     title: "abc",
//     methods: {
//         test(input){
//             //
//         },
//         bla: function(){
//             a
//         }
//     }
// }

//OB1 = {
//CB1 = }
//OB2 = (
//CB2 = )
export function parseObj(str, name){
    str = str.trim();

    var OB1 = str.indexOf("{");
    var CB1 = str.indexOf("}");
    var OB2 = str.indexOf("(");
    var CB2 = str.indexOf(")");

    //MAP
    if(str.startsWith("{") && str.endsWith("}")){
        return parseMap(str.substring(1, str.length-1));
    }
    //VALUE
    else if(OB1 == -1){
        return parseValue(str);
    }
    //FUNCTION ...(...){...}
    else if(OB2 < CB2 && CB2 < OB1 && OB1 < CB1){
        parseFunction(str, name);
    }
    else {
        throw new Error("Cannot parse Object: " + str);
    }
}

function parseFunction(funcStr, name){
    console.log("Parsing Function: " + name + ":" + funcStr);


}

function parseValue(valStr){
    console.log("Parse Value: ", valStr);
    var valJsonStr = '{"value":' + valStr + '}';
    var valJsonObj = JSON.parse(valJsonStr);
    return valJsonObj["value"];
}

function parseMap(mapStr){
    console.log("Parsing Map: " + mapStr);

    var _map = {};

    var i = nextComma(mapStr);
    var before = 0;
    while(i != -1){
        var content = mapStr.substring(before, i);
        parseMapEntry(_map, content);
        
        before = i + 1;
        i = nextComma(mapStr, before);
    }

    if(before < mapStr.length){
        var rest = mapStr.substring(before);
        parseMapEntry(_map, rest);
    }

    return _map;
}

function parseMapEntry(map, contentString){
    var nextColon = contentString.indexOf(":");
    var OB2 = contentString.indexOf("(");

    //Either val:...
    //Or function test(){}

    //val:...
    if((nextColon != -1 && OB2 != -1 && nextColon < OB2) || (nextColon != -1 && OB2 == -1)){
        var entryName = contentString.substring(0, nextColon).trim();
        var entryVal = contentString.substring(nextColon + 1).trim();

        console.log("Entry: [" + entryName + ": " + entryVal + "]");

        var entryObj = parseObj(entryVal, entryName);
        map[entryName] = entryObj;
    }
    // [test(...){...}]
    else if(OB2 != -1){
        var entryName = contentString.substring(0, OB2);
        var entryVal = contentString.substring(OB2);

        console.log("Entry Function: [" + entryName + ": " + entryVal + "]");

        var entryObj = parseFunction(entryVal, entryName);
        map[entryName] = entryObj;
    }
    else {
        throw new Error("Invalid Entry: " + contentString);
    }
}

function nextIndexOutsideOfString(str, find, fromIndex = 0){
    
    var str1 = false; // ""
    var str2 = false; // ''

    for(var i=fromIndex;i<str.length;i++){
        if(str.charAt(i) == '"'){
            str1 = !str1;
        }
        else if(str.charAt(i) == "'"){
            str2 = !str2;
        }
        else if(str1 == false && str2 == false){
            if(str.startsWith(find, i)){
                return i;
            }
        }
    }

    return -1;
}

//{a:0, b:{c:1, d:2}, c:3}
function nextComma(str, fromIndex = 0){
    var str1 = false;
    var str2 = false;

    for(var i=fromIndex;i<str.length;i++){
        var c = str.charAt(i);
        if(str1 == true){
            if(c == '"'){
                str1 = false;
            }
        }
        else if(str2 == true){
            if(c == "'"){
                str2 = false;
            }
        }
        else if(str1 == false && str2 == false){
            if(c == '"'){
                str1 = true;
            }
            else if(c == "'"){
                str2 = true;
            }
            else if(c == ','){
                return i;
            }
            else if(c == '{'){
                var a = i;
                i = nextEqualClosing(str, "{", "}", i + 1);

                if(i == -1) return -1;
            }
        }
    }

    return -1;
}

function nextEqualClosing(str, open, close, fromIndex = 0){
    var nextOpen = nextIndexOutsideOfString(str, open, fromIndex);
    var nextClose = nextIndexOutsideOfString(str, close, fromIndex);
    var open = 1;

    while(true){
        if(nextOpen != -1 && nextOpen < nextClose){
            open++;
            nextOpen = nextIndexOutsideOfString(str, open, nextOpen + 1);
        }
        else if(nextClose != -1 && nextClose < nextOpen){
            open--;

            if(open == 0){
                return nextClose;
            }

            nextClose = nextIndexOutsideOfString(str, close, nextClose + 1);
        }
        else {
            return -1;
        }
    }
}