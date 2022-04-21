// ==UserScript==
// @name         Save hashi boards v2
// @namespace    http://tampermonkey.net/
// @version      0.1
// @require http://code.jquery.com/jquery-3.4.1.min.js
// @description  try to take over the world!
// @author       Sari
// @match        https://menneske.no/hashi/eng/random.html
// @icon         data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==
// @grant        none
// @require http://code.jquery.com/jquery-3.4.1.min.js
// @run-at document-end
// ==/UserScript==

    var $ = window.jQuery;
(function() {
    'use strict';

     var j = 0;
    $(document).ready(function () {
        if (window.location.hash == '#reload') {
            save("hashi_" + j +".txt");
        }
    });



    function reload () {
        window.location.hash = 'reload';
        window.location.reload();
    }



    function save(filename) {
        hashi='';
        var diff='';
        $('div .hashi').each(function(){
            diff+=$(this).html()+',';
        });
        var diffic = diff.match("Difficulty:.+");
        var difficulty = diffic[0].substring(12, diffic[0].indexOf("<"));
        difficulty = difficulty.replaceAll(' ', '_');
        hashi+=difficulty+"\r\n";
        $('table tbody tr .hashi').each(function(){
            var detail='';
            $(this).find('td').each(function(){
                detail+=$(this).html()+',';
            });
            detail=detail.substring(0,detail.length-1);
            detail = detail.replaceAll(' ', '0');
            hashi+=detail+"\r\n";
        });
        var lines = hashi.split('\n');
        lines.splice(1,2);
        var hashi = lines.join('\n');
        const blob = new Blob([hashi], {type: 'text/csv'});
        if(window.navigator.msSaveOrOpenBlob) {
            window.navigator.msSaveBlob(blob, filename);
        }
        else{
            const elem = window.document.createElement('a');
            elem.href = window.URL.createObjectURL(blob);
            elem.download = filename;
            document.body.appendChild(elem);
            elem.click();
            document.body.removeChild(elem);
        }
    }
       for(var i = 0; i < 5; i++){
        console.log(i);
        reload();
    }
})();