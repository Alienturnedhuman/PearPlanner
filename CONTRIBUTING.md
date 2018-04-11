# How to Contribute to RaiderPlanner

**READ THIS DOCUMENT COMPLETELY BEFORE YOU START WORKING ON THE PROJECT**

This document describes the process and guidelines for making contributions to the [RaiderPlanner project](https://github.com/rsanchez-wsu/RaiderPlanner).

Note that [RaiderPlanner](https://rsanchez-wsu.github.io/RaiderPlanner) is an academic project which is part of the course CEG 3120, Introduction to the Design of Information Technology Systems, at Wright State University. As a result, this document includes aspects related to submitting pull requests for course credit.

You are welcome to participate and contribute even if you are not a student in CEG 3120. Simply ignore the part toward the end about course credit for pull requests, grading, and submitting links to Pilot.

## Before Your First Pull Request

There are a few items which **must** be completed **before** you can participate in the RaiderPlanner project. Here is an itemized list (a bit more on each item follows the list):

 * Install and configure the [development toolchain](TOOLCHAIN.md)
 * Join the [RaiderPlanner Slack team](https://raiderplanner.slack.com)
 * If you do not already have a GitHub account, [sign up](https://github.com/join) for one
 * Read the help articles on [setting up Git and GitHub](https://help.github.com/categories/setup/)
 * Fork the [RaiderPlanner repository](https://github.com/rsanchez-wsu/RaiderPlanner); [instructions](https://help.github.com/articles/fork-a-repo)
 * Read [How to Write a Git Commit Message](https://chris.beams.io/posts/git-commit/)

The development toolchain lists required and recommended tools for RaiderPlanner development. The two primary collaborative tools used for RaiderPlanner development are Slack (for IM/chat) and GitHub (for managing code, issues, documentation, etc.). You will need to register accounts for both if you have not done so already. After registering a GitHub account, ensure that you read the documentation on configuring Git and GitHub.

Project contributions are managed by pull requests. Since you will not have permissions to make changes directly to the code in the main project repository, you will need to create a fork in GitHub. You will make your proposed changes in your fork and submit your proposed changes via a pull request. The process is described in greater detail in the next section.

It is extraordinarily important that you read the guidelines for writing a Git commit message. Additionally, commit messages must include references to other issues and/or closures as appropriate. [This article](https://help.github.com/articles/autolinked-references-and-urls/#issues-and-pull-requests) and [this article](https://help.github.com/articles/closing-issues-using-keywords/) discuss how to refer to and how to close an issue with a commit message. Every commit message must have one or more issue references and/or closures.

If you submit a pull request with non-conforming commit mesages, your pull request may be rejected.

Additional [GitHub help](https://help.github.com/) is available on many different topics.

## For Every Pull Request

It is vitally important that you ensure that any contributions you make to the RaiderPlanner project conform to the guidelines established here. Failure to conform these guidelines may result in your contribution being rejected.

If you are a CEG 3120 student and your submission is being made for course credit, rejection of your submission *will prevent you from receiving points* for the assignment. The next section describes the process for obtaining course project credit for your pull request.

 * Ensure that you have cloned your fork of the RaiderPlanner repository
 * Ensure that [Git is setup to sync the original RaiderPlanner repository](https://help.github.com/articles/fork-a-repo/#keep-your-fork-synced)
 * Make sure you have [synchronized your fork](https://help.github.com/articles/syncing-a-fork/) so you have the latest changes
 * Select [an issue](https://github.com/rsanchez-wsu/RaiderPlanner/issues) that your pull request will address
   * If you would like to work on something for which there is not yet an issue written, write a new issue
     * Issues need to be well written
     * The write up of the issue must be clear and describe both the current state as well as the desired end state
     * The issue must include enough detail that another developer in the project could pick up the issue as written and complete the task with minimal additional information
     * If you are unsure, ask for help with writing a new issue
   * "Claim" the issue
     * GitHub issues can only be assigned to collaborators who have push access to the main project
     * Since you will likely not have such access, simply make a comment on the issue indicating that you are working the issue
   * Ensure that any issues you work are present in the main RaiderPlanner project repository and not in your own fork
 * Research the issue and possible resolutions
   * Solicit input from the instructor and/or other developers via comments on the GitHub issue
   * Identify related issues in the project which may impact your issue or which may be impacted by your issue
   * Connect with developers working on related issues to minimize conflicts between the work being performed
   * It is important that you not simply dive into working on something without first fully understanding the issue and also developing a sound approach for a solution
 * Do your work
   * Create a new branch on which to perform your work
     * Doing your work on a branch is very important because GitHub only allows one pull request per branch
     * Using a branch for your work allows you to submit a pull request and then make a new branch to begin work on something else even before your previous pull request is merged
     * Name your branch in a sensible way (e.g., *issue_123*, *chat_network_module*, etc.)
   * Limit changes to the specific issue(s) which you are working
   * If you encounter style problems or other bugs in lines which you are chainging, fix the problem you encounter
   * If you encounter style problems or other bugs outside of the lines which you are changing, make those changes in a separate commit
   * Do not make extraneous or unrelated changes in a commit
   * If you are making changes to the GUI, follow the guidelines for [modifying the GUI](https://github.com/rsanchez-wsu/RaiderPlanner/wiki/Modifying-the-GUI)
     * Additionally, you must provide additional supporting information in the GitHub issue for your GUI change
     * Provide one or more annotated screenshots or video captures of the particular GUI feature/behavior *before* the change (the annotation can be detailed descriptive text and/or visual markers added to the image/video)
     * Provide one or more annotated screenshots or video captures of the particular GUI feature/behavior *after* the change
     * Ensure that the change(s) that you make improves the user experience and does not change/break unrelated functionality
   * Major structural/architectural changes to the project may require significant discussion
     * Discussions may take place via GitHub issues, Slack, or in person
     * All design decisions must be documented in the relevant GitHub issue(s)
     * Supporting documentation (e.g., UML diagrams, use case descriptions, etc.) will be attached to the relevant GitHub issue(s)
   * If in doubt, ask for guidance
 * Test your work
   * There are two primary mechanisms for testing your work:
     * The project build's *check* target, invoked with the command `./gradlew clean check` or `gradlew.bat clean check`
     * The project build's *run* target, invoked with the command `./gradlew run` or `gradlew.bat run`
   * Execute the *check* target and confirm that the build completes successfully with the output `BUILD SUCCESSFUL`
   * Additionally check that your changes did not introduce any new bugs (as reported by FindBugs in its build report) or new style violations (as reported by CheckStyle in its build report)
   * Review the JUnit build report and confirm that test output is as expected
   * Execute the *run* target and confirm that the application behaves as expected
     * Verify that any changes you made produce expected results in the application
     * Ensure that your changes did not break another part of the application
     * Be sure to attempt invalid actions and supply invalid inputs to ensure proper error handling
 * Review your changes
   * Before you commit your changes, make sure to review them
   * The Eclipse *Team Synchronization* and *Git Staging* views provide the ability to view changes before you commit
   * You can also use the commands described in section 2.2 of *Pro Git*
   * **Make sure that only changes you expect are in your workspace**
     * Carefully examine the differences you are preparing to commit and confirm that each change is expected
     * In particular, ensure that none of the following are inadvertently included in a commit
       * Log files
       * Temporary files
       * Line ending flips (the `git status` and `git diff` commands should warn you if this is happening, but it is your responsibility to ensure that line ending changes are not made unnecessarily)
       * Changes to the project metadata files (e.g., `.project`, `.classpath`, etc.)
   * If this is your first contribution to the project, add your name and email to the AUTHORS file (note that names are listed alphabetically by surname)
   * Ensure that you add the current year and your name to the copyright header in any file to which you made significant changes
   * Confirm that you are on the branch you created for the specific issue you are working
   * Each commit should represent a discrete work unit, issue, task, etc.
   * Commits which contain very large changes should be coordinated in advance, or your pull request may be rejected
 * Commit your changes
   * After reviewing your changes stage the files that you will include in the commit
   * Confirm that the staged changes include only those changes which you expect and specifically intend to be a part of the commit you are preparing
   * Write a commit message that conforms to the guidelines in [How to Write a Git Commit Message](https://chris.beams.io/posts/git-commit/)
   * Ensure that your commit message includes references to and/or closures of related issues as appropriate
 * Push your changes
   * Once you have made a commit in your local working copy, you will need to push it to your fork
   * GitHub will see the new commit and automatically present you the option to create a pull request
   * If you are not done with your work, continue working and committing/pushing additional changes as needed
 * Submit your pull request
   * If in doubt, consult the many available resources on pull requests:
     * [This article](https://help.github.com/articles/about-pull-requests/) provides an overview of pull requests
     * [This article](https://help.github.com/articles/creating-a-pull-request/) describes how to create a pull request
     * [This collection help articles](https://help.github.com/categories/collaborating-with-issues-and-pull-requests/) describes all the different aspects of working with pull requests
   * Ensure that the pull request subject line and write up capture the complete scope of work done in the pull request
   * The pull request write up should conform to the guidelines for writing a Git commit message (but it will summarize all of the commits in the pull request)
   * The pull request write up needs to include references to the issues affected by the pull request
 * Review/monitor your pull request
   * View the status page for your pull request and make sure that the diff looks like what you expect (e.g., if you made changes on 200 lines but the pull request page says that there are changes on 5,000 lines then something went wrong)
   * Monitor the Travis CI [build status](https://travis-ci.org/rsanchez-wsu/RaiderPlanner/pull_requests)
     * The page for your pull request will also provide a direct link to the build for your pull request
     * If the build does not pass (i.e., green status) because of an error (i.e., yellow status) or failure (i.e., red status), determine the cause, fix it, and push one or more new commits to fix the problem
   * Confirm that your pull request can be merged without conflict
     * If your pull request status page indicates that there are merge conflicts, you will need to synchronize your fork and resolve the conflicts
     * As you resolve any conflicts with your pull request, be sure to properly preserve and/or integrate conflicting changes
     * **DO NOT** simply overwrite conflicting changes with your own changes
   * The instructor or another project collaborator may request that you make changes/updates to your pull request before it can be merged

## Course Project Credit For Your Pull Request

If you are a student in CEG 3120 then you will be required to submit three project assignments during the course of the academic term. The three project assignments will almost certainly take the form of substantial pull requests to the RaiderPlanner project. If you have an idea for a project assignment that does not take the form of a GitHub pull request, discuss your idea with the instructor so that suitable criteria may be agreed upon.

When crafting a pull request to be submitted as a project assignment, there are several important considerations:

 * The work performed should reflect a level effort commensurate with a project-level assignment in an upper division university class
   * There is no minimum line count, number of issues resolved, or other quantifiable criteria
   * Software development at times involves considerable effort which yields only small visible changes in the software product
   * If you are concerned that your pull request may not encompass an adequate amount of effort or work, address your concern to the instructor immediately; the time to address your concern to the instructor is well ahead of the deadline to ensure you have enough time to make necessary adjustments
   * If the instructor has questions regarding your work on a pull request, you may be asked to provide additional supporting documentation via verbal conversation, Slack, email, GitHub issue comment, wiki page, etc.
 * The assignment deadline is the deadline by which the pull request link must be submitted to the Pilot dropbox for the project assignment
 * When the link is submitted, the pull request must be *complete*
   * A *complete* pull request is one which meets all of the guidelines established in the previous section
   * A pull request which is not complete (e.g., improper commit messages, extraneous changes, missing issue references, failing build, etc.) may be rejected
 * If a pull request is rejected, you will need to fix whatever is wrong and submit a new pull request
   * A link to the new pull request will need to be submitted to the Pilot dropbox
   * If the new link is submitted after the assignment deadline, points may be deducted for being late
 * Pull requests are processed in the order submitted
   * If an earlier pull request is merged and subsequently creates a conflict with your pull request, you will be asked to resolve the conflict
   * If you must resolve conflicts arising from a merge of another pull request, you will not be penalized for being late, assuming that your pull request was otherwise *complete* prior to the deadline
   * You will not be penalized for being late if your pull request is merged after the deadline
 * Project assignment points will be awarded in Pilot once a pull request is merged
   * The URL to the pull request which is merged must match the URL submitted to the Pilot dropbox
   * If a pull request ends up not being merged, then points will not be awarded
 * The instructor may, at his sole discretion, waive the merge requirement
   * A pull request may be rejected in this case because it is too disruptive, not consistent with project goals, or for other reasons
   * If the merge requirement is waived, points will be awarded as for any other pull request
   * All other criteria for a *complete* pull request will still apply, as will the assignment deadline
 * Disputes over points awarded should be addressed directly to the instructor
* Waiting for the last minute is a nearly certain way to ensure that you will run into problems and end up losing points

