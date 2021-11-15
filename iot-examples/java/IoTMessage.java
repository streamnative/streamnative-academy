package io.streamnative.examples.oauth2;

/**
 * https://github.com/tspannhw/minifi-xaviernx/blob/master/demo.py
 */

public class IoTMessage {

    String uuid;
    String camera;
    String ipaddress;
    double networktime;
    double top1pct;
    String top1;
    String cputemp;
    String gputemp;
    String gputempf;
    String cputempf;
    String runtime;
    String host;
    String filename;
    String host_name;
    String macaddress;
    String te;
    String systemtime;
    double cpu;
    String diskusage;
    double memory;
    String imageinput;

    public String getImageinput() {
        return imageinput;
    }

    public void setImageinput(String imageinput) {
        this.imageinput = imageinput;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public double getNetworktime() {
        return networktime;
    }

    public void setNetworktime(double networktime) {
        this.networktime = networktime;
    }

    public double getTop1pct() {
        return top1pct;
    }

    public void setTop1pct(double top1pct) {
        this.top1pct = top1pct;
    }

    public String getTop1() {
        return top1;
    }

    public void setTop1(String top1) {
        this.top1 = top1;
    }

    public String getCputemp() {
        return cputemp;
    }

    public void setCputemp(String cputemp) {
        this.cputemp = cputemp;
    }

    public String getGputemp() {
        return gputemp;
    }

    public void setGputemp(String gputemp) {
        this.gputemp = gputemp;
    }

    public String getGputempf() {
        return gputempf;
    }

    public void setGputempf(String gputempf) {
        this.gputempf = gputempf;
    }

    public String getCputempf() {
        return cputempf;
    }

    public void setCputempf(String cputempf) {
        this.cputempf = cputempf;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getHost_name() {
        return host_name;
    }

    public void setHost_name(String host_name) {
        this.host_name = host_name;
    }

    public String getMacaddress() {
        return macaddress;
    }

    public void setMacaddress(String macaddress) {
        this.macaddress = macaddress;
    }

    public String getTe() {
        return te;
    }

    public void setTe(String te) {
        this.te = te;
    }

    public String getSystemtime() {
        return systemtime;
    }

    public void setSystemtime(String systemtime) {
        this.systemtime = systemtime;
    }

    public double getCpu() {
        return cpu;
    }

    public void setCpu(double cpu) {
        this.cpu = cpu;
    }

    public String getDiskusage() {
        return diskusage;
    }

    public void setDiskusage(String diskusage) {
        this.diskusage = diskusage;
    }

    public double getMemory() {
        return memory;
    }

    public void setMemory(double memory) {
        this.memory = memory;
    }

    public IoTMessage() {
        super();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IoTMessage{");
        sb.append("uuid='").append(uuid).append('\'');
        sb.append(", camera='").append(camera).append('\'');
        sb.append(", ipaddress='").append(ipaddress).append('\'');
        sb.append(", networktime=").append(networktime);
        sb.append(", top1pct=").append(top1pct);
        sb.append(", top1='").append(top1).append('\'');
        sb.append(", cputemp='").append(cputemp).append('\'');
        sb.append(", gputemp='").append(gputemp).append('\'');
        sb.append(", gputempf='").append(gputempf).append('\'');
        sb.append(", cputempf='").append(cputempf).append('\'');
        sb.append(", runtime='").append(runtime).append('\'');
        sb.append(", host='").append(host).append('\'');
        sb.append(", filename='").append(filename).append('\'');
        sb.append(", host_name='").append(host_name).append('\'');
        sb.append(", macaddress='").append(macaddress).append('\'');
        sb.append(", te='").append(te).append('\'');
        sb.append(", systemtime='").append(systemtime).append('\'');
        sb.append(", cpu=").append(cpu);
        sb.append(", diskusage='").append(diskusage).append('\'');
        sb.append(", memory=").append(memory);
        sb.append(", imageinput='").append(imageinput).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public IoTMessage(String uuid, String camera, String ipaddress, double networktime,
                      double top1pct, String top1, String cputemp, String gputemp, String gputempf,
                      String cputempf, String runtime, String host, String filename, String host_name,
                      String macaddress, String te, String systemtime, double cpu, String diskusage,
                      double memory, String imageinput) {
        super();
        this.uuid = uuid;
        this.camera = camera;
        this.ipaddress = ipaddress;
        this.networktime = networktime;
        this.top1pct = top1pct;
        this.top1 = top1;
        this.cputemp = cputemp;
        this.gputemp = gputemp;
        this.gputempf = gputempf;
        this.cputempf = cputempf;
        this.runtime = runtime;
        this.host = host;
        this.filename = filename;
        this.host_name = host_name;
        this.macaddress = macaddress;
        this.te = te;
        this.systemtime = systemtime;
        this.cpu = cpu;
        this.diskusage = diskusage;
        this.memory = memory;
        this.imageinput = imageinput;
    }
}
