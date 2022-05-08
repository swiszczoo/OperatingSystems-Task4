package cz.swisz.so.task4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Sequence {
    public static class Request {
        private final int _address;
        private final int _page;
        private final Process _proc;

        public Request(Process proc, Process.Request req) {
            _address = req.getAddress();
            _page = req.getPage();
            _proc = proc;
        }

        public int getAddress() {
            return _address;
        }

        public int getPage() {
            return _page;
        }

        public Process getProcess() { return _proc; }
    }

    private final ArrayList<Request> _requests;
    private final ArrayList<Process> _processes;
    private int _pageErrors;

    public Sequence(Process[] processes) {
        _requests = new ArrayList<>();
        _processes = new ArrayList<>();
        _pageErrors = 0;

        _processes.addAll(Arrays.asList(processes));

        // Merge all reference queues

        Random rand = new Random();
        int proc = 0;
        int[] opPos = new int[processes.length];
        Arrays.fill(opPos, 0);

        int finished = 0;

        while (finished < processes.length) {
            int curr = opPos[proc];
            if (curr == processes[proc].getRequestCount()) {
                proc = (proc + 1) % processes.length;
                continue;
            }

            int opCount = rand.nextInt(1024) + 1024;

            if (curr + opCount >= processes[proc].getRequestCount()) {
                opCount = processes[proc].getRequestCount() - curr;
                finished++;
            }

            for (int i = 0; i < opCount; i++) {
                Process.Request req = processes[proc].getRequestAt(curr + i);
                _requests.add(new Request(processes[proc], req));
            }

            opPos[proc] += opCount;
            proc = (proc + 1) % processes.length;
        }
    }

    public int getRequestCount() {
        return _requests.size();
    }

    public ArrayList<Process> getProcesses() { return _processes; }

    public Request getRequestAt(int index) {
        return _requests.get(index);
    }

    public void calcTotalPageErrors() {
        _pageErrors = 0;

        for (Process p : _processes) {
            _pageErrors += p.getPageErrors();
        }
    }

    public int getPageErrors() {
        return _pageErrors;
    }

    public Sequence cloneSequence() {
        Process[] processes = new Process[_processes.size()];

        for (int i = 0; i < _processes.size(); i++) {
            processes[i] = _processes.get(i).cloneProcess();
        }

        return new Sequence(processes);
    }
}
