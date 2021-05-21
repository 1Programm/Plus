export default {
    data: {
        title: null,
        word: null,
        c_t_s: {r: 0, g: 0, b: 0},
        c_t_e: {r: 173, g: 216, b: 230},
        c_w_s: {r: 173, g: 216, b: 230}, 
        c_w_e: {r: 0, g: 0, b: 0},
    },

    methods: {
        color(obj){
            return 'rgb(' + obj.r + "," + obj.g + "," + obj.b + ")";
        },
        
        lerp(a, b, u) {
            return (1 - u) * a + u * b;
        },
        
        fade(element, property, start, end, duration) {
            var interval = 10;
            var steps = duration / interval;
            var step_u = 1.0 / steps;
            var u = 0.0;
            const lerp = this.lerp;

            var theInterval = setInterval(function() {
                if (u >= 1.0) {
                    clearInterval(theInterval);
                }
                var r = Math.round(lerp(start.r, end.r, u));
                var g = Math.round(lerp(start.g, end.g, u));
                var b = Math.round(lerp(start.b, end.b, u));
                var colorname = 'rgb(' + r + ',' + g + ',' + b + ')';
                element.style.setProperty(property, colorname);
                u += step_u;
            }, interval);
        },
        
        onHoverTitle(over){
            if(over){
                this.fade(this.title, 'color', this.c_t_s, this.c_t_e, 150);
                this.fade(this.word, 'color', this.c_w_s, this.c_w_e, 150);
            }
            else {
                this.fade(this.title, 'color', this.c_t_e, this.c_t_s, 400);
                this.fade(this.word, 'color', this.c_w_e, this.c_w_s, 400);
            }
        }
    },

    nav: {
        right: "contents"
    },

    onfinish() {
        this.title = document.getElementById("title");
        this.word = document.getElementById("word");

        this.title.style.setProperty('color', this.color(this.c_t_s));
        this.word.style.setProperty('color', this.color(this.c_w_s));
    },
};