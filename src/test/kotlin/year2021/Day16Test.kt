package year2021

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day16Test {

    @Test
    fun `Part 1`() {
        val input = AoCUtils.readText("year2021/Day16.txt")
        Day16().part1(input) shouldBe 957L
    }

    @Test
    fun `Part 2`() {
        val input = AoCUtils.readText("year2021/Day16.txt")
        Day16().part2(input) shouldBe 0
    }

    @Test
    fun `D2FE28`() {
        Day16().part1("D2FE28") shouldBe 6L
    }

    @Test
    fun `38006F45291200`() {
        Day16().part1("38006F45291200") shouldBe 9
    }

    @Test
    fun `EE00D40C823060`() {
        Day16().part1("EE00D40C823060") shouldBe 14
    }

    @Test
    fun `8A004A801A8002F478`() {
        Day16().part1("8A004A801A8002F478") shouldBe 16L
    }

    @Test
    fun `620080001611562C8802118E34`() {
        Day16().part1("620080001611562C8802118E34") shouldBe 12L
    }

    @Test
    fun `C0015000016115A2E0802F182340`() {
        Day16().part1("C0015000016115A2E0802F182340") shouldBe 23L
    }

    @Test
    fun `A0016C880162017C3686B18A3D4780`() {
        Day16().part1("A0016C880162017C3686B18A3D4780") shouldBe 31L
    }


    @Test
    fun `finds the product of 6 and 9, resulting in the value 54`() {
        Day16().part2("04005AC33890") shouldBe 54
    }

    @Test
    fun `finds the minimum of 7, 8, and 9, resulting in the value 7`() {
        Day16().part2("880086C3E88112") shouldBe 7
    }

    @Test
    fun `finds the maximum of 7, 8, and 9, resulting in the value 9`() {
        Day16().part2("CE00C43D881120") shouldBe 9
    }

    @Test
    fun `produces 1, because 5 is less than 15`() {
        Day16().part2("D8005AC2A8F0") shouldBe 1
    }

    @Test
    fun `produces 0, because 5 is not greater than 15`() {
        Day16().part2("F600BC2D8F") shouldBe 0
    }

    @Test
    fun `produces 0, because 5 is not equal to 15`() {
        Day16().part2("9C005AC2F8F0") shouldBe 0
    }

    @Test
    fun `produces 1, because 1 + 3 = 2 * 2`() {
        Day16().part2("9C0141080250320F1802104A08") shouldBe 1
    }

    @Test
    fun `finds the sum of 1 and 2, resulting in the value 3`() {
        Day16().part2("C200B40A82") shouldBe 3
    }
}