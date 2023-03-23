$(function() {

    $(window).scroll(function() {
        console.log('스크롤 중입니다...');
        let scrollTop = $(this).scrollTop()

        if( scrollTop <= 70 ) {
            $('header').stop().fadeIn()
            $('header').removeClass('background-header')
        }
        else {
            $('header').hide()
            $('header').addClass('background-header')
        }
        

    })


})