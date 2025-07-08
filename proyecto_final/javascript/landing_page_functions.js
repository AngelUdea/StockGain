/**
 * LANDING_PAGE_FUNCTIONS.JS
 * 
 * Archivo JavaScript que gestiona las animaciones e interacciones de la página de inicio
 * de la aplicación StockGain. Incluye efectos visuales, animaciones del menú superior,
 * efectos de hover en secciones de características y animaciones de transición.
 * 
 * Funcionalidades principales:
 * - Efectos de scroll en el menú superior (transparencia, blur, sombras)
 * - Animaciones hover en elementos del menú de navegación
 * - Transiciones dinámicas de imágenes de fondo en secciones principales
 * - Efectos visuales en tarjetas de características
 * - Animación de flecha deslizante en la sección de información
 * - Gestión del estado visual del logo en diferentes estados
 * 
 * Dependencias:
 * - DOM elements: elementos con clases específicas del HTML
 * - CSS transitions: Para efectos de transición suaves
 * - BoxIcons: Para iconos de flecha animados
 * 
 * @author StockGain Team
 * @version 1.0
 */

// Obtención de referencias a elementos del DOM para manipulación de la página
const pagina = document.querySelector('.pagina');
const menu_top = document.querySelector('.menu_top');
const menu_top_item = document.querySelectorAll('.menu_top_item');
const menu_top_logo = document.querySelector('.menu_top_logo');
const menu_top_logo_highlight = document.querySelector('.menu_top_logo_highlight');

// Referencias a elementos de imágenes de fondo de la segunda sección
const second_section_bg_image1 = document.querySelector('.second_section_bg_image1');
const second_section_bg_image2 = document.querySelector('.second_section_bg_image2');
const second_section_bg_image3 = document.querySelector('.second_section_bg_image3');
const second_section_bg_image4 = document.querySelector('.second_section_bg_image4');
const second_section_bg_image5 = document.querySelector('.second_section_bg_image5');

// Referencias a elementos de tarjetas de características
const second_section_card = document.querySelectorAll('.second_section_card')
const second_section_card1 = document.querySelector('.second_section_card1');
const second_section_card2 = document.querySelector('.second_section_card2');
const second_section_card3 = document.querySelector('.second_section_card3');
const second_section_card_text1 = document.querySelector('.second_section_card_text1');
const second_section_card_text2 = document.querySelector('.second_section_card_text2');
const second_section_card_text3 = document.querySelector('.second_section_card_text3');

// Referencias a elementos de la tercera sección con animación de flecha
const third_section_container_left = document.querySelector('.third_section_container_left');
const third_section_container_image_container = document.querySelector('.third_section_container_image_container');
const third_section_image_arrow = document.querySelector('.third_section_image_arrow');

/**
 * Event listener para el scroll de la página
 * Gestiona los efectos visuales del menú superior basados en la posición del scroll
 * Aplica transparencia, blur y sombras dinámicamente
 */
pagina.addEventListener('scroll', () => {
    // Verificación de posición en la parte superior de la página
    if (pagina.scrollTop === 0 || pagina.scrollTop < 10) {
        // Aplicación de estilos transparentes para el menú en la parte superior
        menu_top.style.boxShadow = ' 0px 0px 0px 0px rgb(0, 0, 0)';
        menu_top.style.webkitBackDropFilter = 'none';
        menu_top.style.backdropFilter = 'none';
        menu_top.style.background = '#22283100';
    }
    else {
        // Aplicación de estilos con blur y sombra cuando se hace scroll
        menu_top.style.boxShadow = ' 0px 0px 10px 1px #000000e1';
        menu_top.style.borderBottom = ' 1px solid #00000000';
        menu_top.style.webkitBackDropFilter = 'blur(3px)';
        menu_top.style.backdropFilter = 'blur(3px)';
        menu_top.style.background = '#2228318a';
    }
})

/**
 * Event listener para el hover sobre el menú superior
 * Aplica efectos visuales cuando el usuario pasa el mouse por encima del menú
 */
menu_top.addEventListener('mouseenter', () => {
    // Aplicación de efectos visuales solo cuando se está en la parte superior
    if (pagina.scrollTop === 0) {
        menu_top.style.boxShadow = ' 0px 0px 10px 1px #000000e1';
        menu_top.style.borderBottom = ' 1px solid #00000000';
        menu_top.style.webkitBackDropFilter = 'blur(3px)';
        menu_top.style.backdropFilter = 'blur(3px)';
        menu_top.style.background = '#2228318a';
    }
})

/**
 * Event listener para cuando el mouse sale del menú superior
 * Remueve efectos visuales cuando el usuario no está hover en el menú
 */
menu_top.addEventListener('mouseleave', () => {
    // Restauración de la transparencia cuando se está en la parte superior
    if (pagina.scrollTop === 0) {
        menu_top.style.boxShadow = ' 0px 0px 0px 0px rgb(0, 0, 0)';
        menu_top.style.webkitBackDropFilter = 'none';
        menu_top.style.backdropFilter = 'none';
        menu_top.style.background = '#22283100';
    }
})

/**
 * Event listeners para elementos individuales del menú superior
 * Gestiona efectos visuales en los elementos del menú de navegación
 */
menu_top_item.forEach(item => {
    // Efecto al pasar el mouse sobre un elemento del menú
    item.addEventListener('mouseenter', () => {
        menu_top.style.borderBottom = ' 1px solid #00ADB5';
        menu_top.style.box
    });
    // Efecto al salir el mouse de un elemento del menú
    item.addEventListener('mouseleave', () => {
        menu_top.style.borderBottom = ' 1px solid #00000000';
    });
});

/**
 * Event listeners para efectos de hover en el logo
 * Controla la transición entre la versión normal y resaltada del logo
 */
menu_top_logo.addEventListener('mouseenter', () => {
    // Mostrar versión resaltada del logo
    menu_top_logo_highlight.style.display = 'block';
    menu_top_logo.style.display = 'none';
})

menu_top_logo_highlight.addEventListener('mouseleave', () => {
    // Mostrar versión normal del logo
    menu_top_logo_highlight.style.display = 'none';
    menu_top_logo.style.display = 'block';
})

/**
 * Event listeners para efectos de hover en la quinta imagen de fondo
 * Controla la transición entre la cuarta y quinta imagen de fondo
 */
second_section_bg_image5.addEventListener('mouseenter', () => {
    // Transición suave hacia la quinta imagen
    second_section_bg_image4.style.opacity = '0.001';
    second_section_bg_image5.style.opacity = '1';
    second_section_bg_image5.style.transition = 'all cubic-bezier(0,1,1,1) 500ms';
})

second_section_bg_image5.addEventListener('mouseleave', () => {
    // Restauración de la cuarta imagen
    second_section_bg_image4.style.opacity = '1';
    second_section_bg_image5.style.opacity = '0.001';
    second_section_bg_image5.style.transition = 'all linear 500ms';
})

/**
 * Event listeners para efectos de hover en las tarjetas de características
 * Controla las transiciones de imágenes de fondo y textos según la tarjeta seleccionada
 */
second_section_card.forEach(card => {
    card.addEventListener('mouseenter', function (e) {
        // Switch para manejar diferentes efectos según la tarjeta
        switch (true) {
            case this.classList.contains('second_section_card1'):
                // Efectos específicos para la primera tarjeta
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
                // Efectos específicos para la segunda tarjeta
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
                // Efectos específicos para la tercera tarjeta
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
                // Caso de error - no debería ejecutarse en condiciones normales
                alert('¿profe, como hizo esto?');
        }
    })
});

/**
 * Event listeners para efectos de salida del mouse en las tarjetas de características
 * Restaura los estados visuales originales cuando el usuario sale del hover
 */
second_section_card.forEach(card => {
    card.addEventListener('mouseleave', function (e) {
        // Switch para manejar diferentes efectos de salida según la tarjeta
        switch (true) {
            case this.classList.contains('second_section_card1'):
                // Restauración de efectos para la primera tarjeta
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
                // Restauración de efectos para la segunda tarjeta
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
                // Restauración de efectos para la tercera tarjeta
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
                // Caso de error - no debería ejecutarse en condiciones normales
                alert('¿profe, como hizo esto?');
        }
    })
});

// Variable de estado para controlar la animación de la flecha
let arrow_clicked = false;

/**
 * Event listener para la animación de la flecha deslizante
 * Controla la transición de la sección informativa con animación de flecha
 */
third_section_container_image_container.addEventListener('click', () => {
    // Verificación del estado actual de la animación
    if (!arrow_clicked) {
        // Animación de apertura: desliza el contenido hacia la izquierda
        third_section_container_left.style.left = '0';
        third_section_container_image_container.style.left = '-40px';   
        third_section_container_image_container.style.transition = 'all ease-in-out 700ms';
        third_section_image_arrow.classList.remove('bx-tada')
        third_section_image_arrow.style.transform = 'rotate(180deg)';
        arrow_clicked = true;
    } else {
        // Animación de cierre: restaura la posición original
        third_section_container_left.style.left = '25%';
        third_section_container_image_container.style.left = 'calc(-25% + 40px)';
        third_section_container_image_container.style.transition = 'all cubic-bezier(1,-0.99,.59,.7) 700ms';
        third_section_image_arrow.classList.add('bx-tada')
        third_section_image_arrow.style.transform = 'rotate(0deg)';
        arrow_clicked = false;
    }
})