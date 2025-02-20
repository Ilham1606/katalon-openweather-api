import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import groovy.json.JsonSlurper
import groovy.json.JsonBuilder as JsonBuilder
import groovy.json.JsonOutput as JsonOutput
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.util.KeywordUtil
import internal.GlobalVariable

RequestObject testObject = findTestObject('Object Repository/GET_5Days_Forecast')

ResponseObject objectResp = WS.sendRequestAndVerify(testObject, FailureHandling.STOP_ON_FAILURE)


def validateResponseWeather(ResponseObject objectResp) {
	
	KeywordUtil.logInfo('HEADER\n'+objectResp.getHeaderFields()+'\n\nBODY\n'+objectResp.getResponseBodyContent())
	JsonSlurper slurperJson = new JsonSlurper()
	def jsonResp = slurperJson.parseText(objectResp.getResponseText())
	
	GlobalVariable.latitude = jsonResp.city.coord.lat
	KeywordUtil.logInfo('Latitude: ' + jsonResp.city.coord.lat)
	GlobalVariable.longitude = jsonResp.city.coord.lon
	KeywordUtil.logInfo('Longitude: ' + jsonResp.city.coord.lon)
	
	assert jsonResp.cod == "200" :
		"Expected cod = 200, but got ${jsonResp.cod}"
	assert jsonResp.city.name == "Jakarta" :
		"Expected City = Jakarta, but got ${jsonResp.city.name}"
	assert jsonResp.city.country == "ID" :
		"Expected Country = ID, but got ${jsonResp.city.country}"
	assert jsonResp.list[0].weather[0].description == "scattered clouds" :
		"Expected Weather Description = scattered clouds, but got ${jsonResp.list[0].weather[0].description}"
	
	KeywordUtil.logInfo('Expected cod: ' + jsonResp.cod)
	KeywordUtil.logInfo('City Name: ' + jsonResp.city.name)
	KeywordUtil.logInfo('Country Name: ' + jsonResp.city.country)
	KeywordUtil.logInfo('Weather Description: ' + jsonResp.list[0].weather[0].description)

}

validateResponseWeather(objectResp)



