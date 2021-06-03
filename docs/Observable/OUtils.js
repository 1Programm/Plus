export function Assure(statement, message, path){
    if(!statement) {
        if(!path){
            path = "";
        }
        else {
            path = "[" + path + "]: ";
        }
        
        throw new Error(path + message);
    }
}