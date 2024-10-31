@skip
Feature: Application Inquiry Via Hash / UniqueID
	As a user
	I want to be able to send a hashcode
	So that I can verify if I have an existing CCS application
	
	Scenario: Applications Status based on Examples
		Given the hash API
		When I enter "<hash>"
		Then I should get <status> as Hash API result
		
		Examples:
		|	hash							|	status	|
		|	samplehash						|	200		|
		|	samplehash						|	404		|	