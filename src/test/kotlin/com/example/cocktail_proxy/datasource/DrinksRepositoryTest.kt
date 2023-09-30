package com.example.cocktail_proxy.datasource

import com.example.cocktail_proxy.model.DrinkJpaCompatible
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DrinksRepositoryTest {

    @Autowired
    private lateinit var drinksRepository: DrinksRepository

    @Test
    fun `should validate that methods of DrinksRepository interface are auto-implemented`() {
        // given
        val drink = DrinkJpaCompatible(
            idDrink = 10,
            strDrink = "Drink",
            date = "2023-12-12",
            id = 1
        )

        //when
        drinksRepository.save(drink)

        //then
        assertThat(drinksRepository.findByIdDrink(drink.idDrink)).isNotNull
        assertThat(drinksRepository.findByIdDrink(2)).isNull()

        assertThat(drinksRepository.findByDate(drink.date!!)).isNotNull
        assertThat(drinksRepository.findByDate("")).isNull()

        assertThat(drinksRepository.existsByIdDrink(drink.idDrink)).isTrue()
        assertThat(drinksRepository.existsByIdDrink(2)).isFalse()
    }
}