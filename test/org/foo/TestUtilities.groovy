/**
 * Created by d049997 on 26/04/2017.
 */

import org.junit.Test;
import org.junit.Rule
import org.jvnet.hudson.test.JenkinsRule;
import org.apache.commons.io.FileUtils;
import jenkins.plugins.git.GitSampleRepoRule;
import org.jenkinsci.plugins.workflow.libs.GlobalLibraries
import org.jenkinsci.plugins.workflow.libs.LibraryConfiguration
import org.jenkinsci.plugins.workflow.libs.SCMSourceRetriever
import jenkins.plugins.git.GitSCMSource;
import org.jenkinsci.plugins.workflow.job.WorkflowJob;
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition;
import org.jenkinsci.plugins.workflow.job.WorkflowRun;

class TestUtilitiles {

    @Rule public JenkinsRule r = new JenkinsRule();

    @Rule public GitSampleRepoRule sampleRepo = new GitSampleRepoRule();


    @Test
    public void foo() throws Exception{
        sampleRepo.init();
        try {
            FileUtils.copyDirectory(new File("src"), new File(sampleRepo.getRoot(), "src"));
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        sampleRepo.git("add", "src");
        sampleRepo.git("commit", "--message=init");
        GlobalLibraries.get().setLibraries(Collections.singletonList(new LibraryConfiguration("utils", new SCMSourceRetriever(new GitSCMSource(null, sampleRepo.getRoot().getAbsolutePath(), "", "*", "", true)))));

        WorkflowJob p = r.jenkins.createProject(WorkflowJob.class, "p");

        p.setDefinition(new CpsFlowDefinition("""
@Library('utils@master') import org.foo.Utilities
def utils = new Utilities(steps)
node {
    utils.foo()
}
"""));

        WorkflowRun b = r.assertBuildStatusSuccess(p.scheduleBuild2(0))
        r.assertLogContains("first", b)
        r.assertLogContains("two", b)
    }
}