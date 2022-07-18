# Picnic Recruitment Task #

This assignment was created specifically for our QA recruitment process.
You were given a link to GitHub, which when you visited that link, created a private fork
of this repository. Only you and the developers at Picnic can see the code you push to this
repository. If you did not receive a link, please contact us. Please read the following
instructions carefully and try to ensure that you fulfill all requirements listed.

## Task ##

Your task is:

1. Create a test strategy document for the API behavior of GitHub Gists. Please consider following endpoints (authenticated & unauthenticated):
   * [Creating a Gist][gists-create]
   * [Reading a Gist][gists-read]
   * [Updating a Gist][gists-update]
   * [Deleting a Gist][gists-delete]
   * Listing Gists for a user ([authenticated][gists-all] & [unauthenticated][gists-all-public])

2. Your Test strategy should contain:
   * QA role/involvement overview in testing activities
   * Overview of main test scenarios
   * Test scenarios for manual and/or automation tests. Please put your focus on providing extensive coverage of the functionality
   * Prioritization of tests
   * The reasoning behind certain tests

You might want to consider the definition of ready, acceptance criteria, or alike for this. You are free to use any kind of documenting and presenting: i.e. PowerPoint, google sheet etc.

3. Based on the chosen strategy, we ask you to write automation tests for the functional behavior of Gists (2 or 3 based on your preferences).
While you do this, please follow the technical requirements listed below. Please note, you are not limited only to the mentioned endpoints.

**A note before you start**
Familiarize yourself with official GitHub documentation as a single source of functional requirements for API behavior. We expect you to test both authorized and unauthorized scenarios where applicable.


## Technical Requirements ##

1. Programming language (choose one):
   * Python
   * Java

2. Test approach (choose one): 
   * Traditional xUnit test code style
   * BDD (Gherkin-based) testing frameworks

## How to hand in your assignment ##

1. Make a local clone of this repository on your machine, and do your work on a
   branch other than `master`. Do not make any changes to the `master` branch.
2. Push your changes as frequently as you like to `origin/your-branch-name`,
   and create a pull request to merge your changes back into the `master`
   branch. Don't merge your pull request. Once you're finished with the
   assignment, we will do a code review of your pull request.
3. When you're finished, [create and add][github-labels] the label `done` to
   your pull request and let your contact person know. Please do **NOT** publish your solution 
   on a publicly available location (such as a public GitHub repository, your personal website,
   _et cetera_).

This process closely mimics our actual development and review cycle. We hope
you enjoy it!

## Resources ##

* Gists API Documentation: [GitHub Gists][github-gists]
* For [authorization][github-oauth2] you can [generate][github-tokens] Personal OAuth token with `gists` scope


_Thanks in advance for your time and interest in Picnic!_

[github-labels]: https://help.github.com/articles/about-labels
[github-gists]: https://developer.github.com/v3/gists/
[github-tokens]: https://github.blog/2013-05-16-personal-api-tokens/
[github-oauth2]: https://developer.github.com/v3/#oauth2-token-sent-in-a-header
[gists-all]: https://developer.github.com/v3/gists/#list-gists-for-the-authenticated-user
[gists-all-public]: https://developer.github.com/v3/gists/#list-gists-for-a-user
[gists-read]: https://developer.github.com/v3/gists/#get-a-gist
[gists-create]: https://developer.github.com/v3/gists/#create-a-gist
[gists-update]: https://developer.github.com/v3/gists/#update-a-gist
[gists-delete]: https://developer.github.com/v3/gists/#delete-a-gist
