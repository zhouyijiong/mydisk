let login_status = 0;
let login_url = '/user/login';
window.onload = function () {
    if (!sessionStorage.product) {
        ajax.base(
            'GET',
            '/config/getPublicKey',
            null,
            (response) => {
                sessionStorage.product = response.data;
            }
        );
    }
    document.getElementById('usm').focus();
}

function updateState(obj) {
    let current, loginView;
    if (++login_status % 2 === 0) {
        loginView = '登录';
        current = 'registered';
        login_url = '/user/login';
    } else {
        loginView = '注册';
        current = 'login';
        login_url = '/user/registered';
    }
    let login = document.getElementById('login');
    obj.innerHTML = current;
    login.value = loginView;
}

function judgment() {
    if (!navigator.cookieEnabled) {
        alert('cookie 被禁用,请开启再使用本站');
        return;
    }
    let usm = document.getElementById('usm');
    if (!checkStr(usm.value, 3, 8)) {
        alert('用户名只能由3~10位字符或数字组成');
        usm.value = '';
        usm.focus();
        return
    }
    let pwd = document.getElementById('pwd');
    if (!checkStr(pwd.value, 6, 16)) {
        alert('密码只能由6~16位字符或数字组成');
        pwd.value = '';
        pwd.focus();
        return
    }
    let requestParam = {};
    let temp;
    requestParam.username = usm.value;
    requestParam.password = md5(pwd.value);
    requestParam.platform = (temp = navigator.platform) ? temp : '';
    requestParam.language = (temp = navigator.language) ? temp : '';
    requestParam.cores = (temp = navigator.deviceMemory) ? temp : 0;
    requestParam.thread = (temp = navigator.hardwareConcurrency) ? temp : 0;
    requestParam.network = (temp = navigator.connection) ? (temp = temp.downlink) ? temp : 0 : 0;
    ajax.post(login_url,
        requestParam,
        (response) => {
            if (response.message) {
                pwd.value = '';
                usm.focus();
            } else {
                let data = JSON.parse(response.data);
                sessionStorage.token = data.token;
                sessionStorage.access = data.access;
                setCookie('identity', data.identity, 7);
                window.location.href = '/management';
            }
        });
}

document.getElementById('usm').addEventListener('keyup', ({keyCode}) => {
    if (keyCode === 13) document.getElementById('pwd').focus();
});
document.getElementById('pwd').addEventListener('keyup', ({keyCode}) => {
    if (keyCode === 13) judgment();
});