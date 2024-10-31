@skip
Feature: Email Verification via OTP
	As a user
	I want to be able to receive an OTP from email
	So that I can verify the ownership of the given email
	
	Scenario: Valid Email
		Given the Email API
		When I enter "<email>" address
		Then I should get <status> as valid email
		
		Examples:
		|	email							|	status	|
		|	test@gmail.com					|	200		|
	
	
	Scenario: Invalid Email
		Given the Email API
		When I enter "<email>" address
		Then I should get <status> as invalid email
		Examples:
		|	email							|	status	|
		|	@ibayad.com						|	400		|
		|	randomword123###				|	400		|
		|	nonexisting@ibayad.com.ph.xyz	|	400		|