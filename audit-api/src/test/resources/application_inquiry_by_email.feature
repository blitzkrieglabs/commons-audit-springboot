Feature: Application Inquiry Via Email
	As a user
	I want to be able to send my email address
	So that I can verify if I have an existing CCS application
	
	Scenario: Application Status based on Examples
		Given the Email API
		When I enter "<email>" address
		Then I should get <status> as application inquiry result
		
		Examples:
		|	email							|	status	|
		|	test@gmail.com					|	200		|
		|	@ibayad.com						|	400		|
		|	randomword123###				|	400		|
		|	invalid@ibayad.com.ph,xyz		|	400		|
		|	nonexisting@ibayad.com			| 	404		|
		