pipeline{

	agent any
	
	stages{
		stage("Build"){
			steps{
				echo("Build project")
			}
		}
		
		stage("Run UTs"){
			steps{
				echo("Run unit test cases")
			}
		}
		
		stage("Deploy to DEV"){
			steps{
				echo("Deploy code to Dev environment")
			}
		}
		
		stage("Deploy to QA"){
			steps{
				echo("Deploy to QA environment")
			}
		}
		
		stage("Run automation regression test cases"){
			steps{
				echo("Automation regression test cases")
			}
		}
		
		stage("Deploy to Stage"){
			steps{
				echo("Automation sanity test cases")
			}
		}
		
		stage("Deploy to Prod"){
			steps{
				echo("Deploy code to Production")
			}
		}
		
		
	}	




}