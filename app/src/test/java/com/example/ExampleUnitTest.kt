package com.example

import com.example.data.model.ContentTemplateCategory
import com.example.data.model.ToneOfVoice
import com.example.data.service.ContentTemplateLibrary
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun contentTemplateLibrary_containsAllRequiredCategories() {
        val allTemplates = ContentTemplateLibrary.templates
        assertTrue("Template library should contain at least 10 templates", allTemplates.size >= 10)

        val emailTemplates = ContentTemplateLibrary.getTemplatesByCategory(ContentTemplateCategory.EMAIL_NEWSLETTER)
        assertTrue("Email templates should not be empty", emailTemplates.isNotEmpty())

        val landingPageTemplates = ContentTemplateLibrary.getTemplatesByCategory(ContentTemplateCategory.LANDING_PAGE)
        assertTrue("Landing page templates should not be empty", landingPageTemplates.isNotEmpty())

        val productTemplates = ContentTemplateLibrary.getTemplatesByCategory(ContentTemplateCategory.PRODUCT_DESCRIPTION)
        assertTrue("Product description templates should not be empty", productTemplates.isNotEmpty())
    }

    @Test
    fun contentTemplateLibrary_generatesCustomizedOutput() {
        val template = ContentTemplateLibrary.templates.first { it.id == "tmpl_email_welcome_story" }
        val inputs = mapOf(
            "brandName" to "GrowthPulse AI",
            "founderName" to "Elena Rostova",
            "personalStruggle" to "spending 14 hours every weekend manually writing content",
            "breakthrough" to "building an autonomous GEO optimization framework",
            "welcomeGift" to "a 10-Step GEO Blueprint Checklist",
            "ctaUrl" to "https://growthpulse.ai/blueprint"
        )
        val output = ContentTemplateLibrary.generateCustomizedTemplateCopy(
            template = template,
            userInputs = inputs,
            tone = ToneOfVoice.AUTHORITATIVE_GEO
        )

        assertNotNull(output)
        assertTrue(output.fullBodyContent.contains("GrowthPulse AI"))
        assertTrue(output.fullBodyContent.contains("Elena Rostova"))
        assertTrue(output.characterCount > 50)
        assertTrue(output.wordCount > 10)
    }
}

