package cz.swisz.so.task4;

import java.util.ArrayList;

public class Process {
    public static class Request {
        private final int _time; // Ignored in Task 3
        private final int _address;
        private final int _page;

        public Request(int time, int address) {
            _time = time;
            _address = address;

            _page = Utils.convertAddressToPage(address);
        }

        public int getTime() {
            return _time;
        }

        public int getAddress() {
            return _address;
        }

        public int getPage() {
            return _page;
        }
    }

    private final ArrayList<Request> _requests;
    private int _pageErrors;

    public Process() {
        _requests = new ArrayList<>();
        _pageErrors = 0;
    }

    public void addRequest(Request request) {
        _requests.add(request);
    }

    public int getRequestCount() {
        return _requests.size();
    }

    public Request getRequestAt(int index) {
        return _requests.get(index);
    }

    public void increasePageErrors() {
        ++_pageErrors;
    }

    public int getPageErrors() {
        return _pageErrors;
    }

    public Process cloneProcess() {
        Process clone = new Process();
        clone._requests.addAll(_requests);
        clone._pageErrors = _pageErrors;

        return clone;
    }
}
