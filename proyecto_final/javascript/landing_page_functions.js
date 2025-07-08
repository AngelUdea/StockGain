const pagina = document.querySelector('.pagina');
const menu_top = document.querySelector('.menu_top');
const menu_top_item = document.querySelectorAll('.menu_top_item');
const menu_top_logo = document.querySelector('.menu_top_logo');
const menu_top_logo_highlight = document.querySelector('.menu_top_logo_highlight');
const second_section_bg_image1 = document.querySelector('.second_section_bg_image1');
const second_section_bg_image2 = document.querySelector('.second_section_bg_image2');
const second_section_bg_image3 = document.querySelector('.second_section_bg_image3');
const second_section_bg_image4 = document.querySelector('.second_section_bg_image4');
const second_section_bg_image5 = document.querySelector('.second_section_bg_image5');
const second_section_card = document.querySelectorAll('.second_section_card')
const second_section_card1 = document.querySelector('.second_section_card1');
const second_section_card2 = document.querySelector('.second_section_card2');
const second_section_card3 = document.querySelector('.second_section_card3');
const second_section_card_text1 = document.querySelector('.second_section_card_text1');
const second_section_card_text2 = document.querySelector('.second_section_card_text2');
const second_section_card_text3 = document.querySelector('.second_section_card_text3');
const third_section_container_left = document.querySelector('.third_section_container_left');
const third_section_container_image_container = document.querySelector('.third_section_container_image_container');
const third_section_image_arrow = document.querySelector('.third_section_image_arrow');

pagina.addEventListener('scroll', () => {
    if (pagina.scrollTop === 0 || pagina.scrollTop < 10) {
        menu_top.style.boxShadow = ' 0px 0px 0px 0px rgb(0, 0, 0)';
        menu_top.style.webkitBackDropFilter = 'none';
        menu_top.style.backdropFilter = 'none';
        menu_top.style.background = '#22283100';
    }
    else {
        menu_top.style.boxShadow = ' 0px 0px 10px 1px #000000e1';
        menu_top.style.borderBottom = ' 1px solid #00000000';
        menu_top.style.webkitBackDropFilter = 'blur(3px)';
        menu_top.style.backdropFilter = 'blur(3px)';
        menu_top.style.background = '#2228318a';
    }
})

menu_top.addEventListener('mouseenter', () => {
    if (pagina.scrollTop === 0) {
        menu_top.style.boxShadow = ' 0px 0px 10px 1px #000000e1';
        menu_top.style.borderBottom = ' 1px solid #00000000';
        menu_top.style.webkitBackDropFilter = 'blur(3px)';
        menu_top.style.backdropFilter = 'blur(3px)';
        menu_top.style.background = '#2228318a';
    }
})

menu_top.addEventListener('mouseleave', () => {
    if (pagina.scrollTop === 0) {
        menu_top.style.boxShadow = ' 0px 0px 0px 0px rgb(0, 0, 0)';
        menu_top.style.webkitBackDropFilter = 'none';
        menu_top.style.backdropFilter = 'none';
        menu_top.style.background = '#22283100';
    }
})

menu_top_item.forEach(item => {
    item.addEventListener('mouseenter', () => {
        menu_top.style.borderBottom = ' 1px solid #00ADB5';
        menu_top.style.box
    });
    item.addEventListener('mouseleave', () => {
        menu_top.style.borderBottom = ' 1px solid #00000000';
    });
});

menu_top_logo.addEventListener('mouseenter', () => {
    menu_top_logo_highlight.style.display = 'block';
    menu_top_logo.style.display = 'none';
})

menu_top_logo_highlight.addEventListener('mouseleave', () => {
    menu_top_logo_highlight.style.display = 'none';
    menu_top_logo.style.display = 'block';
})

second_section_bg_image5.addEventListener('mouseenter', () => {
    second_section_bg_image4.style.opacity = '0.001';
    second_section_bg_image5.style.opacity = '1';
    second_section_bg_image5.style.transition = 'all cubic-bezier(0,1,1,1) 500ms';
})

second_section_bg_image5.addEventListener('mouseleave', () => {
    second_section_bg_image4.style.opacity = '1';
    second_section_bg_image5.style.opacity = '0.001';
    second_section_bg_image5.style.transition = 'all linear 500ms';
})

second_section_card.forEach(card => {
    card.addEventListener('mouseenter', function (e) {
        switch (true) {
            case this.classList.contains('second_section_card1'):
                second_section_bg_image1.style.opacity = '1';
                second_section_bg_image2.style.opacity = '0.001';
                second_section_bg_image3.style.opacity = '0.001';
                second_section_bg_image4.style.opacity = '0.001';
                second_section_bg_image5.style.display = 'none';
                second_section_bg_image1.style.transform = 'scale(1.05)';
                second_section_bg_image2.style.transform = 'scale(1)';
                second_section_bg_image3.style.transform = 'scale(1)';
                second_section_bg_image4.style.transform = 'scale(2)';
                second_section_card_text1.style.textShadow = '0px 0px 5px #00000000';
                second_section_card_text2.style.textShadow = '0px 0px 5px #00000000';
                second_section_card_text3.style.textShadow = '0px 0px 5px #00000000';
                second_section_card_text1.style.color = '#00ADB5';
                second_section_card_text2.style.color = '#00000000';
                second_section_card_text3.style.color = '#00000000';
                second_section_card_text3.style.bottom = '0px';
                break;
            case this.classList.contains('second_section_card2'):
                second_section_bg_image1.style.opacity = '0.001';
                second_section_bg_image2.style.opacity = '1';
                second_section_bg_image3.style.opacity = '0.001';
                second_section_bg_image4.style.opacity = '0.001';
                second_section_bg_image5.style.display = 'none';
                second_section_bg_image1.style.transform = 'scale(1)';
                second_section_bg_image2.style.transform = 'scale(1.05)';
                second_section_bg_image3.style.transform = 'scale(1)';
                second_section_bg_image4.style.transform = 'scale(2)';
                second_section_card_text1.style.textShadow = '0px 0px 5px #00000000';
                second_section_card_text2.style.textShadow = '0px 0px 5px #00000000';
                second_section_card_text3.style.textShadow = '0px 0px 5px #00000000';
                second_section_card_text1.style.color = '#00000000';
                second_section_card_text2.style.color = '#00ADB5';
                second_section_card_text3.style.color = '#00000000';
                second_section_card_text3.style.bottom = '0px';
                break;
            case this.classList.contains('second_section_card3'):
                second_section_bg_image1.style.opacity = '0.001';
                second_section_bg_image2.style.opacity = '0.001';
                second_section_bg_image3.style.opacity = '1';
                second_section_bg_image4.style.opacity = '0.001';
                second_section_bg_image5.style.display = 'none';
                second_section_bg_image1.style.transform = 'scale(1)';
                second_section_bg_image2.style.transform = 'scale(1)';
                second_section_bg_image3.style.transform = 'scale(1.05)';
                second_section_bg_image4.style.transform = 'scale(2)';
                second_section_card_text1.style.textShadow = '0px 0px 5px #000000';
                second_section_card_text2.style.textShadow = '0px 0px 5px #000000';
                second_section_card_text3.style.textShadow = '0px 0px 5px #000000';
                second_section_card_text1.style.color = '#00ADB5';
                second_section_card_text2.style.color = '#00ADB5';
                second_section_card_text3.style.color = '#00ADB5';
                second_section_card_text3.style.bottom = '0px';
                break;
            default:
                alert('¿profe, como hizo esto?');
        }
    })
});

second_section_card.forEach(card => {
    card.addEventListener('mouseleave', function (e) {
        switch (true) {
            case this.classList.contains('second_section_card1'):
                second_section_bg_image1.style.opacity = '0.001';
                second_section_card_text1.style.textShadow = '0px 0px 5px #00000000';
                second_section_card_text2.style.textShadow = '0px 0px 5px #00000000';
                second_section_card_text3.style.textShadow = '0px 0px 5px #00000000';
                second_section_card_text1.style.color = '#00000000';
                second_section_card_text2.style.color = '#00000000';
                second_section_card_text3.style.color = '#00000000';
                second_section_card_text3.style.bottom = '-50px';
                break;
            case this.classList.contains('second_section_card2'):
                second_section_bg_image2.style.opacity = '0.001';
                second_section_card_text1.style.textShadow = '0px 0px 5px #00000000';
                second_section_card_text2.style.textShadow = '0px 0px 5px #00000000';
                second_section_card_text3.style.textShadow = '0px 0px 5px #00000000';
                second_section_card_text1.style.color = '#00000000';
                second_section_card_text2.style.color = '#00000000';
                second_section_card_text3.style.color = '#00000000';
                second_section_card_text3.style.bottom = '-50px';
                break;
            case this.classList.contains('second_section_card3'):
                second_section_bg_image3.style.opacity = '0.001';
                second_section_bg_image4.style.opacity = '1';
                second_section_bg_image4.style.transform = 'scale(1)';
                second_section_bg_image5.style.display = 'block';
                second_section_card_text1.style.textShadow = '0px 0px 5px #eeeeee';
                second_section_card_text2.style.textShadow = '0px 0px 5px #eeeeee';
                second_section_card_text3.style.textShadow = '0px 0px 5px #eeeeee';
                second_section_card_text1.style.color = '#eeeeee';
                second_section_card_text2.style.color = '#eeeeee';
                second_section_card_text3.style.color = '#eeeeee';
                second_section_card_text3.style.bottom = '0px';
                break;
            default:
                alert('¿profe, como hizo esto?');
        }
    })
});
let arrow_clicked = false;
third_section_container_image_container.addEventListener('click', () => {
    if (!arrow_clicked) {
        third_section_container_left.style.left = '0';
        third_section_container_image_container.style.left = '-40px';   
        third_section_container_image_container.style.transition = 'all ease-in-out 700ms';
        third_section_image_arrow.classList.remove('bx-tada')
        third_section_image_arrow.style.transform = 'rotate(180deg)';
        arrow_clicked = true;
    } else {
        third_section_container_left.style.left = '25%';
        third_section_container_image_container.style.left = 'calc(-25% + 40px)';
        third_section_container_image_container.style.transition = 'all cubic-bezier(1,-0.99,.59,.7) 700ms';
        third_section_image_arrow.classList.add('bx-tada')
        third_section_image_arrow.style.transform = 'rotate(0deg)';
        arrow_clicked = false;
    }
})