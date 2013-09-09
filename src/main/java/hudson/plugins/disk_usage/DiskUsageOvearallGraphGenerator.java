/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hudson.plugins.disk_usage;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.model.PeriodicWork;
import hudson.model.TopLevelItem;
import java.io.File;
import jenkins.model.Jenkins;

/**
 *
 * @author jbrazdil
 */
@Extension
public class DiskUsageOvearallGraphGenerator extends PeriodicWork {

	@Override
	public long getRecurrencePeriod() {
		return PeriodicWork.DAY;
	}

	@Override
	protected void doRun() throws Exception {
            DiskUsagePlugin plugin = Jenkins.getInstance().getPlugin(DiskUsagePlugin.class);
            plugin.refreshGlobalInformation();
            File jobsDir = new File(Jenkins.getInstance().getRootDir(), "jobs");
            Long freeJobsDirSpace = jobsDir.getTotalSpace();
            
            plugin.getHistory().add(new DiskUsageRecord(plugin.getCashedGlobalBuildsDiskUsage(), plugin.getGlobalWorkspacesDiskUsage(), plugin.getCashedGlobalJobsWithoutBuildsDiskUsage(), freeJobsDirSpace));
            plugin.save();
	}

}
