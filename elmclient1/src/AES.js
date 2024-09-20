import CryptoJS from 'crypto-js';
import { enc } from 'crypto-js';

export default{ //默认访问

    generateKey(num) {
        let library = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        let key = "";
        for(var i=0;i<num;i++){
            let randomPoz = Math.floor(Math.random() * library.length);
            key += library.substring(randomPoz,randomPoz+1);
        }
        return key;
    },

    encrypt(word, keyStr){
        keyStr = keyStr ? keyStr : 'abcdsxyzhkj12345';
        var key = CryptoJS.enc.Utf8.parse(keyStr);
        var srcs = CryptoJS.enc.Utf8.parse(word);
        var encrypted = CryptoJS.AES.encrypt(srcs, key, { mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7 });
        return encrypted.toString();
    },

    decrypt(word, keyStr) {
        keyStr = keyStr ? keyStr : 'abcdsxyzhkj12345';
        var key = CryptoJS.enc.Utf8.parse(keyStr);
        var decrypt = CryptoJS.AES.decrypt(word, key, { mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7 });
        return CryptoJS.enc.Utf8.stringify(decrypt).toString();
    }
}