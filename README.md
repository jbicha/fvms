The FVMS project is tested on Ubuntu 12.04 LTS and Ubuntu 12.10.

1. Open a terminal and run `sudo apt-get install git eclipse glassfish-javaee`

    (Also install eclipse-mylyn for optional git integration)

1. `git clone https://github.com/jbicha/fvms.git`
1. Download the latest version of [JBoss][] and extract to your home folder.
1. Run Eclipse. Click _Help_ > _Install New Software_.
1. Click _Add_.

    For Name use _JBoss Tools_ and for Location use _http://download.jboss.org/jbosstools/updates/stable/indigo_.

1. Check _Abridged JBoss Tools_ and click _Next_ to begin installation.

    Click OK when it tells you the software is unsigned.

    It will prompt you to restart when finished.

…

1. Open [http://localhost:8080/fvms/][] in your web browser.

[JBoss]: http://www.jboss.org/jbossas/downloads/
[http://localhost:8080/fvms/]: http://localhost:8080/fvms/
