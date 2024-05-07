package com.postnov.feedbacks.utils

class Constants {
    companion object {
        const val HELLO_MESSAGE = """
            Добро пожаловать в WB Feedbacks Bot!
Данный бот предоставляет краткую выжимку 
негативных отзывов товара на wildberries 
по его артикулу. Вам нужно просто ввести 
номер артикула. Через некоторые время бот 
напишет основные негативные отзывы товара. 
----------------------------------------------------------
Введите номер артикула:
        """
        const val ERROR_PRODUCT_ID = "Неправильный артикул. Попробуйте ввести заново.\nВведите номер артикула: "
    }
}