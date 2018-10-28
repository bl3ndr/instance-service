package com.kyzrlabs.bl3ndr.authservice

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.assertEquals


@RunWith(SpringRunner::class)
@SpringBootTest(classes = [ECService::class])
internal class ECServiceTest {

    @Autowired
    lateinit var testee: ECService

    @Test
    fun testECDHExchange(){
        val privateKeyStringBob = "00EC9A592BFE20CFA34D6771219F171ACF40D16B539BFC8A0A3AFA8BEA743A63F9"
        val pkStringBob = "0426BC97E4A78AA3EB96514E826F2E563C25BA87697A6CDF71A7FB795CE930C314135E6CB71A51952B17489E61E95568CDFF1BFFCE9DAB59E3733C6E1CF883D31A"

        val privateKeyStringAlice = "25260d65ac38613b00fd9e89db705da93f63ead2a1ad2bc89dd1ea81bc40bdc3"
        val pkStringAlice = "049997ae1ffb6e02979c5e893da4686fc698abe6c9878d6e7d422aa2fb1f0ed171efaaa29aea165ea76c02f2dd8c1710cd9d46795012736f5238deb5102a089e03"

        val secretBob = testee.exchangeDH(privateKeyStringBob, pkStringAlice)
        val secretAlice = testee.exchangeDH(privateKeyStringAlice, pkStringBob)

        assertEquals(secretBob, secretAlice);
    }

}