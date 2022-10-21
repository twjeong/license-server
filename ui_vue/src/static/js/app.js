export const app = {
  ready: function ($) {
    $('body#login').fadeIn(300)

    var btnAdduser = $('.login_input input:last-child')
    $(btnAdduser).on('click', function () {
      $('.login_input').css({ transform: 'translateX(-6rem)', opacity: 0 })
      $('.adduser_input').css({'z-index':0}).css({'transform':'translateX(5rem)', 'opacity':1})
    })

    $('.gologin').on('click', function() {
        $('.login_input').css({'transform':'translateX(0)', 'opacity':1})
        $('.adduser_input').css({'z-index':-1}).css({'transform':'translateX(30rem)', 'opacity':0})
    })    

    var menuOpen = true
    function menuToggle() {
        if (menuOpen == true) {
            $('nav').css('left', '-25rem')
            $('.contents_wrap').css({'left':'0','width':'100%'})
            $('footer').css({'left':'0','width':'100%'})
        } else {
            $('nav').css('left', '0')
            $('.contents_wrap').css({'left':'25rem','width':'calc(100% - 25rem)'})
            $('footer').css({'left':'25rem','width':'calc(100% - 25rem)'})
        }
        menuOpen = !menuOpen
    }

    $('.btn_hambuger').on('click', function() {
        menuToggle()
    })

    function openInIframe(url) {
        $('.iframe_contents').attr('src', url)
    }

    function removeClassOn_Menu() {
        $(menu).parent().parent().find('li').removeClass('on')
    }

    var menu = $('nav ul li a')
    $(menu).on('click', function(e) {
        e.preventDefault()
        $('.iframe_contents').css('display', 'none')
        removeClassOn_Menu()
        $(this).parent().addClass('on')
        var crrMenuUrl = $(this).attr('href')
        openInIframe(crrMenuUrl)
        $('.iframe_contents').fadeIn(300)
    })

    $('.account_v').on('click', function() {
        $('.account_menu').slideToggle(200)
    })
    $('.account_menu a').on('click', function() {
        $('.account_menu').slideToggle(200)
    })

    $('.contents_wrap iframe').fadeIn(300)

    $('.icon_show').on('click', function() {
        var eye_posx = $(this).offset().left
        var eye_posy = $(this).offset().top
        $('.balloon').offset( { left: eye_posx + 70 , top: eye_posy - 70 })
    })

    $('.table_basic tbody tr, .fixed_table tbody tr').on('click', function() {
        var selected = $(this).hasClass('on')
        $('.table_basic tbody tr, .fixed_table tbody tr').removeClass('on')
        $('.table_basic tbody tr, .fixed_table tbody tr').children().css('color', '#000')
        if(!selected) {
            $(this).addClass('on')
            $(this).children().css('color', '#fff')
        }
    })
  }
}
