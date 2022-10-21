$(document).ready(function() {

    /*----------------  IE일 경우 처리  -------------*/
    // var agent = navigator.userAgent.toLowerCase();
    // if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ) {
    //     console.log("인터넷 익스플로러 브라우저 입니다.");
    //
    //     $(".datepicker").datepicker({ dateFormat: 'yy .mm. dd'});  /* IE일경우 html5 input[date] 대신에 jquery ui datepicker 적용 */
    //     $(".timepicker").timepicker({    /* IE일경우 html5 input[time] 대신에 jquery ui timepicker 적용 */
    //         timeFormat: 'p hh:mm',
    //         interval: 30,   // 30분 단위
    //         minTime: '00:01 am',
    //         maxTime: '23:59 pm',
    //         // defaultTime: '11:00',
    //         startTime: '01:00',
    //         dynamic: false,
    //         dropdown: true,
    //         scrollbar: true
    //     });
    // }

    /*---------------- jquery ui datepicker 한글화  -------------*/ 
    $.datepicker.setDefaults({  // datepicker 한글화 적용 
        dateFormat: 'yymmdd',
        prevText: '이전 달',
        nextText: '다음 달',
        monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
        monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
        dayNames: ['일', '월', '화', '수', '목', '금', '토'],
        dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
        dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
        showMonthAfterYear: true,
        yearSuffix: '년',
        showButtonPanel: true,	// 오늘로 가는 버튼과 달력 닫기 버튼 보기 옵션
        buttonImageOnly: true,	// input 옆에 조그만한 아이콘으로 캘린더 선택가능하게 하기
        buttonImage: "image/icon_calender.png",	// 조그만한 아이콘 이미지
        buttonText: "Select date"	// 조그만한 아이콘 툴팁
    });

    /*======================= 컨텐츠 화면 상단으로 스크롤 이동 ============================*/

    // 컨텐츠 내용이 많은 페이지를 스크롤 해서 보다가 컨텐츠가 적은 페이지로 전환했을 경우 스크롤이 이전 페이지 보던 위치로 내려가 있는 현상 방지
    if ( $(top.document).find(".contents_wrap").scrollTop() > 0 ) {    // iframe 을 포함한 영역의 위치가 아래로 내려가 있다면
        $(top.document).find(".contents_wrap").animate( { scrollTop : 0 }, 400, function()
        { /* console.log("위로 가요~~"); */ } );   // 최상단으로 슬라이딩해서 이동한다.
    }

    /*-------------------- 페이지 로딩 시 발생 이벤트 --------------------------*/
    $(".section_block").css({'transform':"translateY(0)"});    // 각각의 섹션 블럭 자기 위치로 설정하기
    $(".setupOk").delay(400).fadeIn(300);   // 설정페이지 확인 버튼 로딩 시 나타나기

    /*-------------------- Select Menu - Readonly 상태 (클릭안되게 하기) --------------------------*/
    $(".readonly").css('pointer-events','none');   // readonly 클래스 적용된 객체 클릭 안되게 하기    

    //=============  스위치 버튼 클릭 시 : 텍스트 on/off 변경하기  ================//
    $("div.btn_switch").one('click', function(e) {    // one() 는 이벤트 중복 방지 (한번만 실행)
        $(this).children().eq(0).children("input[id=btn_toggle]").off().on("change", function() {   // off() 는 이벤트 중복 방지 
            if ( $(this).is(":checked") ) {   // "ON" 활성 상태면
                $(this).parent().parent().children().eq(1).css("display", "none");   // OFF 숨김
                $(this).parent().parent().children().eq(2).css("display", "inline-block");   // ON 보임                 
            } else {       //---------------- "OFF" 비활성 상태면
                $(this).parent().parent().children().eq(1).css("display", "inline-block");   // OFF 보임
                $(this).parent().parent().children().eq(2).css("display", "none");   // ON 숨김                            
            }
        });
    });


    //===================================  대시보드와 가이드 페이지 선별 이벤트  =====================================//

    if ( $("#dashboard").length > 0 ) {    // dashboard 페이지에서만

        /*---------------- jquery circle-progress chart 애니메이션  -------------*/

        function chartAni(chart, value, size, percent) {
            // $('.second.circle.chart1').circleProgress({
            $(chart).circleProgress({
                value : value,   // 실제 그래프로 표시되는 퍼센트
                size : size,     // 원그래프 크기(canvas size, px)
                startAngle : 300,      // 애니메이션 시작 위치 (300: 12시, 설정안하면: 9시)
                lineCap : "round",     // 그래프 양끝모양 (round, butt, square)
                thickness : 12,    // 그래프 두께(px), 기본은 원 크기의 1/14 이지만 설정가능
                fill : {     // 그래프 색상
                    // color : "#f66363"    // 단일 색상
                    gradient : [ ["#12af92", .3], ["#edb456", .55], ["#f66363", 1] ],    // 3개 색상 green, yellow, red (시작, 끝)
                    gradientAngle: Math.PI / 1
                },
                animation : {          // 애니메이션 시간
                    duration: 2000,      // 1초
                    easing : "circleProgressEasing"      // 애니메이션 효과, circleProgressEasing, swing
                }
            }).on('circle-animation-progress', function(event, progress) {
                $(this).find('strong').html(Math.round(percent * progress) + '<i>%</i>');  // 텍스트로 표시되는 퍼센트
            });
        }

        chartAni(".second.circle.chart1", 0.45, 130, 45);  // 메모리 사용현황 차트 애니메이션하기
        chartAni(".second.circle.chart2", 0.9, 130, 90);  // 디스크 사용현황 차트 애니메이션하기
        chartAni(".second.circle.chart3", 0.3, 130, 30);  // 디스크 사용현황 차트 애니메이션하기

    }

    //===================================  List Menu  =====================================//
    function isDoubleClicked(element) {
        //if already clicked return TRUE to indicate this click is not allowed
        if (element.data("isclicked")) return true;

        //mark as clicked for 1 second
        element.data("isclicked", true);
        setTimeout(function () {
            element.removeData("isclicked");
        }, 1000);

        //return FALSE to indicate this click was allowed
        return false;
    }

    var main_list = $(".list_menu > ul > li > a");      // 메인 메뉴
    var sub_list = $(".list_menu > ul ul li");       // 서브 메뉴
    var mmNum = "0";      // 현재 클릭한 메인 메뉴 인덱스, 처음에 첫번째 메인메뉴 선택
    var smNum = "0";     // 현재 클릭한 서브 메뉴 인덱스
    var arrNum = $(".list_menu").children("ul").length;     //  현재 1depth ul 메뉴의 갯수 가져와 배열로 할당하기
    var expand = [];    // 1depth 메인메뉴의 펼침여부 배열, 최초 모두 열린상태로 설정. 메뉴가 늘어나면 값도 추가한다.
    for (var i = 0; i < arrNum; i++) {
        expand.push(1);  // 1depth 메인메뉴의 펼침여부 배열, 모두 열린상태. 메뉴가 늘어나면 값도 추가한다.
    }

    /*-------------------- 1 Depth 메뉴 --------------------------*/

    // 더블클릭과 클릭 동시 사용시 처리하는 함수
    jQuery.fn.single_double_click = function(single_click_callback, double_click_callback, timeout) {
        return this.each(function() {
            var clicks = 0, self = this;
            jQuery(this).click(function(event) {
                clicks++;
                if (clicks == 1) {
                    setTimeout(function() {
                        if(clicks == 1) {
                            single_click_callback.call(self, event);
                        } else {
                            double_click_callback.call(self, event);
                        }
                        clicks = 0;
                    }, timeout || 200);
                }
            });
        });
    }

    /* 메인메뉴 클릭 시 서브메뉴 펼치고 닫기  */
    $(main_list).single_double_click(function() {   // 더블클릭과 클릭 동시 사용시 중복 방지를 위한 이벤트
        if (isDoubleClicked($(this))) return;

        // 클릭 시 수행할 코드
        mmNum = $(this).parent().parent().index();    // 현재 노드의 인덱스 저장 (부모인 ul의 index)
        $(this).parent().parent().find("ul").stop().slideToggle("fast");   // 서브 메뉴 펼침 토글

        // 1 Depth 메뉴 활성화
        $(this).parent().parent().parent().children().children().children("a").removeClass("on");   // 전체 1 Depth 메뉴 모두 비활성화
        $(this).addClass("on");    // 클릭한 1 Depth 메뉴 활성화 배경으로 변경

        if (expand[mmNum] == 1) {      // 현재 메뉴가 열려 있으면
            $(this).parent().children(".tri_arrow").css({"transform" : "rotateZ(0)"});       // 화살표 방향 ^
            expand[mmNum] = 0;      // 닫힘 상태로 변경
        } else {            // 현재 메뉴가 닫혀있으면
            $(this).parent().children(".tri_arrow").css({"transform" : "rotateZ(-180deg)"});       // 화살표 방향 V
            expand[mmNum] = 1      // 열림 상태로 변경
        }
        // console.log("리스트 메뉴 클릭 시 상태 " + expand);
    }, function() {     /* 메인 메뉴 더블클릭: 모두 접기  */
        // 더블클릭 시 수행할 코드
        $(main_list).parent().parent().find("ul").stop().slideUp("fast");   // 모든 메인메뉴 접음
        $(main_list).parent().children(".tri_arrow").css({"transform" : "rotateZ(0)"});       // 화살표 방향 ^
        for (var i = 0; i < arrNum; i++) {
            expand[i] = 0;  // 모두 닫힘 상태로 변경
        }
    });

    /*-------------------- 2 Depth 메뉴 --------------------------*/

    $(sub_list).click(function() {  /*-------------- 서브 메뉴 클릭 시 이벤트 */
        mmNum = $(this).parent().parent().parent().index();   // 현재 클릭한 서브메뉴 포함된 메인메뉴 인덱스 저장
        smNum = $(this).index();    // 현재 클릭한 서브메뉴의 인덱스 저장

        /* 활성화 중인 서브메뉴 모두 비활성화 */
        $(sub_list).children("a").removeClass("on");

        /* 클릭한 서브메뉴 활성화 */
        $(this).children("a").addClass("on");      // 서브 메뉴 활성화

        /* 클릭한 서브의 메인메뉴 활성화 */
        var mm = $(main_list).parent().parent("ul").eq(mmNum).children().children("a");     // 클릭된 서브메뉴가 포함된 메인메뉴
        $(main_list).parent().parent("ul").children().children("a").removeClass("on");   // 메인메뉴 모두 비활성화
        $(mm).addClass("on")  // 선택된 서브메뉴의 메인메뉴 활성화

        /* 클릭한 서브메뉴의 해당 컨텐츠 활성화  */
        var crtPage = "#content_" + mmNum + smNum  // 현재 컨텐츠 클래스명 = content_ + 메인메뉴 index + 서브메뉴 index

        $("div[id^='content_']").hide();      // 모든 컨텐츠 영역 숨김 (display none)
        $("div[id^='content_'] .box").css("transform","translateY(100px)");   // 모든 컨텐츠의 box 원위치로

        $(crtPage).show();         // 현재 선택 컨텐츠 보이기
        $(crtPage + " .box").css('opacity',1);        // 현재 선택한 컨텐츠의 box 보이기
        $(crtPage + " .box").css("transform","translateY(0)");   // 현재 선택한 컨텐츠의 box 순서대로 보이기
    });

    /*-------------------- 고객사명(list_customer) 리스트 관련 이벤트 --------------------------*/

    if ( $("div.list_menu").hasClass("list_customer") === true ) {   // 계약정보현황 페이지의 리스트 메뉴라면
        expand[0] = 0;     // 첫번째 항목을 열린 상태로 설정한다.
        for (var i = 0; i < arrNum; i++) {
            if ( i == 0 ) {   // 첫번째 메뉴면
                expand[i] = 1;     // 열림 상태로
            } else {
                expand[i] = 0;  // 나머지 메뉴는 모두 닫힘 상태로 변경
            }
        }
    }

    $(".cts_product").css("display", "none");  // 페이지 로딩 시 제품상세 테이블을 숨김
    var customer_level1 = $(".list_customer > ul > li > a");      // 메인 메뉴
    var customer_level2 = $(".list_customer > ul > li > ul > li > a");      // 서브 메뉴

    $(customer_level1).parent().parent().find("ul").stop().slideUp("fast");   // 모든 메인메뉴 접음
    $(customer_level1).parent().children(".tri_arrow").css({"transform" : "rotateZ(0)"});       // 화살표 방향 v
    $(customer_level1).parent().parent().find("ul").eq(0).stop().slideDown("fast");   // 첫번째 메인메뉴만 펼침
    $(customer_level1).parent().parent().eq(0).children("li").children(".tri_arrow").css("transform", "rotateZ(-180deg)");   // 첫번째 메인메뉴만 화살표 방향 ^

    /* 1 Depth 메뉴 클릭 */
    $(customer_level1).on("click", function() {
        $(".cts_company").show();  // 고객사상세 테이블을 보임
        $(".cts_product").hide();  // 제품상세 테이블을 숨김

        var title_name = ".list_right > h3 > span";    // 선택한 타이틀 적용할 곳 h3
        var customer_name = "table.cts_company > tbody > tr:nth-child(1) > td";  // 고객사명 표시 td
        var val_t = $(this).text();    // 선택한 메뉴의 text 값을 가져옴
        $(title_name).text(val_t);   // 타이틀을 변경
        $(customer_name).text(val_t);   // 고객사명 변경
    });

    /* 2 Depth 메뉴 클릭 */
    $(customer_level2).on("click", function() {
        $(".cts_product").show();  // 고객사상세 테이블을 숨김
        $(".cts_company").hide();  // 제품상세 테이블을 보임

        var title_name = ".list_right > h3 > span";    // 선택한 타이틀 적용할 곳 h3
        var product_name = "table.cts_product > tbody > tr:nth-child(1) > td";  // 제품명 표시 td
        var val_t = $(this).parent().parent().parent().children("a").text();    // 선택한 서브메뉴의 1Depth 메뉴 text 값을 가져옴
        var val_p = $(this).text();    // 선택한 서브메뉴의 text 값을 가져옴 (제품)
        $(title_name).text( val_t + " - " + val_p );   // 타이틀을 변경
        $(product_name).text(val_t);   // 제품명 변경
    });


    /*======================= 대시보드 차트 그리기 코드 ============================*/

    var ia, ib, ic, func01, func02, func03;
    function color01(ia) {   // 메모리 사용 현황 차트 설정
        $(".pie_chart1").css({ "background":"conic-gradient(#12af92 0% " + ia + "%, #fff " + ia + "% 100%)" });
    }        
    function drawChart01() {
        ia = 1;
        func01 = setInterval(function() {
            if( ia < 45 ) {
                color01(ia);
                ia++;
            } else {
                clearInterval(func01);
            }
        }, 10);
    }
    function color02(ib) {   // 디스크 사용 현황 차트 설정
        $(".pie_chart2").css({ "background":"conic-gradient(#f66363 0% " + ib + "%, #fff " + ib + "% 100%)" });
    }        
    function drawChart02() {
        ib = 1;
        func02 = setInterval(function() {
            if( ib < 90 ) {
                color02(ib);
                ib++;
            } else {
                clearInterval(func02);
            }
        }, 10);
    }    
    function color03(ic) {   // CPU 사용 현황 차트 설정
        $(".pie_chart3").css({ "background":"conic-gradient(#12af92 0% " + ic + "%, #fff " + ic + "% 100%)" });
    }        
    function drawChart03() {
        ic = 1;
        func03 = setInterval(function() {
            if( ic < 30 ) {
                color03(ic);
                ic++;
            } else {
                clearInterval(func03);
            }
        }, 10);
    }     
    drawChart01();  // 메모리 사용 현황 차트 애니메이션 실행
    drawChart02();  // 디스크 사용 현황 차트 애니메이션 실행
    drawChart03();  // CPU 사용 현황 차트 애니메이션 실행

    /*======================= 팝업 윈도우 Show/hidden 애니메이션 ============================*/

    /*-------------------- 팝업윈도 초기 및 브라우저 리사이징 시 위치 설정 --------------------------*/
    var popupWin_width; var browserWidth; var popupwin_left;
     browserWidth = $(window).width(); // 브라우저 가로 (현재문서의 너비)
    //browserWidth = window.outerWidth; // 브라우저 가로 (사용가능한 전체 너비)

    $(window).resize(function() {     /* 윈도우가 리사이징 되면.. */
         browserWidth = $(window).width(); // 브라우저 가로 (현재문서의 너비)
        //browserWidth = window.outerWidth; // 브라우저 가로 (사용가능한 전체 너비)
        $(top.document).find(".popup_win").each( function(index, item) {    // 팝업윈도우를 모두 검사하여
            if ( $(item).css("opacity") != 0 ) {     // 현재 보이는 팝업윈도우 라면
                //popupWin_width = $(top.document).find( $(item) ).width();    // 현재 보이는 팝업창의 가로폭을 구한다
                popupWin_width = $(item).width();    // 현재 보이는 팝업창의 가로폭을 구한다
                popupwin_left = (browserWidth / 2) - (popupWin_width / 2);  // 팝업창의 위치
                //$(top.document).find( $(item) ).css({"left" : popupwin_left});    // 실제 팝업의 위치를 실시간으로 변경
                $(item).css({"left" : popupwin_left});    // 실제 팝업의 위치를 실시간으로 변경
            }
        }); 
    });

    /*-------------------- 팝업 창 열고 닫기 함수 --------------------------*/
    
    // $(top.document)  -- 부모 문서 접근자
    function openPopUpWin(popupwin) {      // 팝업창 열기 함수 ---- 인자1: 팝업창 선택자
        //$(top.document).find("#overlay").css({"opacity":1,"z-index":99});   // 오버레이 보이고 레이어 올리기
        $("#overlay").css({"opacity":1,"z-index":99});   // 오버레이 보이고 레이어 올리기
        $(popupwin).css({"opacity":1, "transform":"translateY(0)", "z-index":999, "overflow-y": "auto"});   // 팝업윈도우 보이고 레이어 올리기

        popupWin_width = $(popupwin).width();   // 팝업윈도우 가로 값을 가져와 할당함
        popupwin_left = (browserWidth / 2) - (popupWin_width / 2);  // 팝업의 위치 (브라우저가로/2 - 팝업창가로/2)
        $(popupwin).css({"left" : popupwin_left + "px"});  // 실제 팝업의 위치를 설정

        // $(popupwin).on('scroll touchmove mousewheel', function(event) {  /* 브라우저 높이 작을 때 팝업윈도 내부 테이블 컬럼헤드 스크롤 방지 */
        //     event.preventDefault();
        //     event.stopPropagation();
        //     return false;
        //   });
    }
    function closePopUpWin(popupwin) {  // 팝업창 닫기 함수 -- 인자1: 팝업창 선택자
        $(popupwin).css({"opacity":0, "transform":"translateY(100px)", "z-index":-1});   // 팝업창 원래대로 숨기고
        //$(top.document).find("#overlay").css({"opacity":0, "z-index":-1});  // 오버레이 숨기고 레이어 낮추기
        $("#overlay").css({"opacity":0, "z-index":-1});  // 오버레이 숨기고 레이어 낮추기
    }

    /*======================= 사용자 추가 > 사용자 추가 팝업 윈도우 열기 이벤트 ============================*/
    $(".user_add_complete").on("click", function(e) {   // 사용자 추가 팝업창 열기
        openPopUpWin( $(".pw_user_add_complete") );    // 팝업창 열기 함수 호출
    });

    /*======================= 비밀번호 변경 > 비밀번호 변경 완료 팝업 윈도우 열기 이벤트 ============================*/
    $(".password_change_complete").on("click", function(e) {   // 비밀번호 변경 팝업창 열기
        openPopUpWin( $(".pw_password_change_complete") );    // 팝업창 열기 함수 호출
    });

    /*======================= 설정 > 알람 서버 정보 등록 팝업 윈도우 열기 이벤트 ============================*/
    $(".email_add_complete").on("click", function(e) {   // 알람 서버 정보 등록 팝업창 열기
        openPopUpWin( $(".pw_email_add_complete") );    // 팝업창 열기 함수 호출
    });

    /*======================= 설정 > 알람 서버 정보 삭제 팝업 윈도우 열기 이벤트 ============================*/
    $(".email_delete_complete").on("click", function(e) {   // 알람 서버 정보 등록 팝업창 열기
        openPopUpWin( $(".pw_email_delete_complete") );    // 팝업창 열기 함수 호출
    });

    /*======================= 계약 정보 현황 > 계약 정보 추가 팝업 윈도우 열기 이벤트 ============================*/
    $(".pw_file_password").on("click", function(e) {   // 계약 정보 파일 불러오기 팝업창 열기
        openPopUpWin( $(".pw_contract_file_load") );    // 팝업창 열기 함수 호출
        $(".input_contract_file_password").focus();
    });

    $(".btn_contract_file_apply").on("click", function(e) {  // 계약 정보 현황 팝업창 열기
        closePopUpWin( $(".pw_contract_file_load") );   // 모든 팝업창 닫기 함수 호출
        openPopUpWin( $(".pw_contract_add") );    // 팝업창 열기 함수 호출
    });

    /*======================= 계약 정보 현황 > 계약 정보 삭제 팝업 윈도우 열기 이벤트 ============================*/
    $(".btn_contract_remove").on("click", function(e) {     // 계약 정보 현황 팝업창 열기
        openPopUpWin( $(".pw_contract_remove") );    // 팝업창 열기 함수 호출
    });

    /*======================= 라이선스 발급 팝업 윈도우 열기 이벤트 ============================*/
    $(".btn_license_issue").on("click", function() {     // 라이선스 발급 팝업창 열기
        if (isDoubleClicked($(this))) return;

        let checkbox = $("input[name=CbxComponentInfo]:checked");
        if (checkbox.length === 0) {
            alert("발급 대상 제품을 하나 이상 선택하시기 바랍니다.");
            return;
        }

        openPopUpWin( $(".pw_licenseissue") );    // 팝업창 열기 함수 호출
    });

    $(".btn_license_discard").on("click", function() {     // 라이선스 폐기 팝업창 열기
        if (isDoubleClicked($(this))) return;

        let checkbox = $("input[name=CbxComponentInfo]:checked");
        if (checkbox.length === 0) {
            alert("폐기 대상 제품을 하나 이상 선택하시기 바랍니다.");
            return;
        }

        openPopUpWin( $(".pw_licensediscard") );    // 팝업창 열기 함수 호출
    });

    $(".btn_license_discard_confirm").on("click", function() {     // 라이선스 폐기 재확인 팝업창 열기
        if (isDoubleClicked($(this))) return;

        let checkbox = $("input[name=CbxComponentInfo]:checked");
        if (checkbox.length === 0) {
            alert("폐기 대상 제품을 하나 이상 선택하시기 바랍니다.");
            return;
        }

        closePopUpWin($(".popup_win"));
        openPopUpWin( $(".pw_licensediscard_confirm") );    // 팝업창 열기 함수 호출
    });

    /*======================= 로그 > 시간설정 팝업 윈도우 열기 이벤트 ============================*/
    $(".btn_timesetup").on("click", function() {     // 시간설정 팝업창 열기
        //openPopUpWin( $(top.document).find(".pw_timesetup") );    // 팝업창 열기 함수 호출
        openPopUpWin( $(".pw_timesetup") );    // 팝업창 열기 함수 호출
    });

    /*======================= 이용 약관 팝업 윈도우 열기 이벤트 ============================*/
    $(".btn_userAgreement").on("click", function() {
        openPopUpWin( $(".pw_user_agreement") );
    });

    /*======================= 모든 팝업 윈도우 닫기 이벤트 ============================*/
    // $(top.document).find(".btn_cancel").on("click", function() {           //  라이선스 발급 팝업창 닫기
    //     closePopUpWin( $(top.document).find(".popup_win") );   // 모든 팝업창 닫기 함수 호출
    // });

    $(".btn_cancel").on("click", function() {           //  라이선스 발급 팝업창 닫기
        closePopUpWin( $(".popup_win") );   // 모든 팝업창 닫기 함수 호출
    });

    /*------------------- 테이블 컬럼 클릭 시 Row 정렬 이벤트  ----------------------*/
    $('table.tbl_colsort thead tr th').on('click', function() {  // 테이블 헤더 클릭 시 컬럼정렬
        var idx = $(this).index();    // th 인덱스
        var target_table = $(this).parent().parent().parent();  // 클릭한 테이블
        if($(this).children('span.arrowicon').hasClass('flag')) {  // th 아이콘이 내림순 모양이면
            $(this).children('span.arrowicon').removeClass('flag');  // flag 제거
            $(this).children('span.arrowicon').text('↓');   // 내림순 모양으로 변경
        } else {     // 아이콘이 올림순 모양이면
            $(this).children('span.arrowicon').addClass('flag');  // flag 추가
            $(this).children('span.arrowicon').text('↑'); // 올림순 모양으로 변경
        }
        // 여기에서 테이블 컬럼 정렬 함수 호출해 주면됨
    });

    /*======================= 테이블 row 클릭 시 배경 변경 이벤트 ============================*/
    // $(".table_basic tbody tr, .fixed_table tbody tr").on("click", function() {  // 기본테이블과 tbody 스크롤되는 테이블에만 적용
    //     if (isDoubleClicked($(this))) return;
    //
    //      var selected = $(this).hasClass("on");   // on 클래스 존재 여부
    //      $(".table_basic tbody tr, .fixed_table tbody tr").removeClass("on");   // 전체 on 클래스 제거
    //      $(".table_basic tbody tr, .fixed_table tbody tr").children().css("color", "#000");
    //      if(!selected) {    // on 클래스 없으면
    //          $(this).addClass("on");  // on 클래스 추가(배경 변경)
    //          $(this).children().css("color", "#fff");   // 텍스트 흰색으로 변경
    //      }
    // });
});

// 문서가 로딩되기 전에 함수가 존재해야 하므로 $(document).ready(function() { } 함수 밖에 놓는다
/*-------------------- IFrame 높이 자동 조절하기 --------------------------*/
window.onload = function() {
    if (window.parent.document.getElementById('ifrm') !== null) {
        window.parent.document.getElementById('ifrm').height =
            window.parent.document.getElementById('ifrm').contentWindow.document.body.clientHeight;  // iframe 의 높이를 내부 문서 높이로 맞춘다.
    }
};
