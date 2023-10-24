import axios from "axios";

export default class EmployeeService {

        url="http://localhost:8080/api";

    getAll() {
        return axios.get(this.url + "/data_list/employee");
    }

    save(employee) {
        return axios.post(this.url + "/employee", employee);
    }

    delete(id) {
        return axios.delete(this.url + "/employee/" + id);
    }

}