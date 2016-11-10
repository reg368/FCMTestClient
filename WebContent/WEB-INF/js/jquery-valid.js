//身份證驗證
function valid_uid(text) {
    if (/^[A-Z](1|2)\d{8}$/.test(text)) {
        var head = "ABCDEFGHJKLMNPQRSTUVXYWZIO";
        var ret = 0;
        ret = head.indexOf(text.substr(0, 1)) + 10;
        ret = Math.floor(ret / 10) + (ret % 10 * 9);
        for (var i = 1; i < text.length - 1; i++) {
            ret += text.substr(i, 1) * (9 - i);
        }
        ret += text.substr(9, 1) * 1;
        return ret % 10 == 0;
    }
    return false;
}

function vf_uid(fid) {
    var v = $(fid).val();
    if (!valid_uid(v)) {
        alert("身分證字號有誤")
        $(fid).focus();
        return false;
    }
    return true;
}

function valid_num(text) {
    return /^[-+]?\d+(\.\d+)?$/.test(text);
}

function valid_email1(value) {
    return  /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/i.test(value)
}

function valid_email(fid, vid) {
    var value = $(vid, fid).val();
    return valid_email1(value);
}

function vf_email(fld_id, fld_name) {
    var value = $(fld_id).val();
    if (!/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/i.test(value)) {
        alert('「' + fld_name + '」為格式有誤，請確認輸入');
        $(fld_id).focus();
        return false;
    }
    return true;
}



function valid_length(fld, len) {
    if ($(fld).val().length > len) {
        alert('長度超過' + len);
        $(fld).focus();
        return false;
    }
    return true;
}

function valid_empty(fld, dv) {
    var v = $(fld).val();
    if ('' == v || dv == v) {
        alert('欄位不能為空');
        $(fld).focus();
        return false;
    }
    return true;
}

function vf_empty_msg(fid, msg) {
    var v = $(fid).val();
    if (v == '') {
        alert(msg);
        $(fid).focus();
        return false;
    }
    return true;
}

function vf_empty(fid, fld_name, dv) {
    var v = $(fid).val();
    if (v == '' || v == dv) {
        alert('「' + fld_name + '」為必填，請確認必填欄位已輸入');
        $(fid).focus();
        return false;
    }
    return true;
}

function vf_num(fid, fld_name) {
    var v = $(fid).val();
    if (!/^[-+]?\d+(\.\d+)?$/.test(v)) {
        alert('「' + fld_name + '」，必須是數字');
        $(fid).focus();
        return false;
    }
    return true;
}

function vf_positive(fid, fname) {
    var v = $(fid).val();
    if (v < 0) {
        alert('「' + fname + '」，請務必輸入正整數或0');
        $(fid).focus();
        return false;
    }
    return true;
}

function valid_num(fld) {
    var v = $(fld).val();
    if (!/^[-+]?\d+(\.\d+)?$/.test(v)) {
        alert('必須是數字');
        $(fld).focus();
        return false;
    }
    return true;
}

function valid_date(year, month, day) {
    var d = new Date(year, month - 1, day);
    return (d.getFullYear() == year && d.getMonth() == month - 1 && d.getDate() == day);
}

function vf_date(year, month, day, fld_name) {
    var year = $(year).val();
    var month = $(month).val();
    var day = $(day).val();
    if (!valid_date(year, month, day)) {
        alert('「' + fld_name + '」，日期未填或格式錯誤');
        $(year).focus();
        return false;
    }
    return true;
}

function fld_clean(fld, dv) {
    $(fld).click(function () {
        if ($(this).val() == dv) {
            $(this).val('');
        }
    });
}

