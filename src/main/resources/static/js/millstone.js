var millstone = {};

/**
 * millstone 환경 변수 저장 공간
 * @type {Object} millstone.def
 * @example
 * ```js
 * millstone.def.menuHeight = 20;
 * // 와 같이 원하는 속성을 저장 / 사용 할 수 있다.
 * ```
 */
millstone.def = {
    "pageFunctionName": "fnObj"
}

/**
 * document ready 상태가 되었을 때 실행, 애플리케이션 초기화 담당
 * @method millstone.init
 */
millstone.init = function () {
    millstone.pageStart();
}

/**
 * millstone.def.pageFunctionName의 pageStart 실행
 * @method millstone.pageStart
 *
 */
millstone.pageStart = function () {
    window[millstone.def.pageFunctionName].pageStart();
};

$(document.body).ready(function () {
    millstone.init();
});

window.onError = function(){
    window.CollectGarbage && window.CollectGarbage();
};

window.onUnload = function () {
    window.CollectGarbage && window.CollectGarbage();
};
