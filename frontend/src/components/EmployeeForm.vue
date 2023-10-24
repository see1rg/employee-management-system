<template>
  <div style="margin: 0 auto; width: 80%">
    <Toast />
    <Panel header="Employee List">
      <Menubar :model="items"/>
      <br>
      <DataTable :value="employees" :paginator="true" :rows="10" selection-mode="single" :selection.sync="selectedEmployee" dataKey="id">
        <Column field="id" header="ID"></Column>
        <Column field="firstName" header="firstName"></Column>
        <Column field="lastName" header="lastName"></Column>
        <Column field="department" header="department"></Column>
        <Column field="salary" header="salary"></Column>
      </DataTable>
    </Panel>
    <Dialog header="Create Employee"
            :visible.sync="displayModal"
            :modal="true">
            <span class="p-float-label">
              <InputText id="firstName" type="text" v-model="employee.firstName" style="width: 100%"/>
              <label for="firstName">firstname</label>
            </span>
      <br>
      <span class="p-float-label">
              <InputText id="lastName" type="text" v-model="employee.lastName" style="width: 100%"/>
              <label for="lastName">lastname</label>
            </span>
      <br>
      <span class="p-float-label">
              <InputText id="department" type="text" v-model="employee.department" style="width: 100%"/>
              <label for="department">department</label>
            </span>
      <br>
      <span class="p-float-label">
              <InputText id="salary" type="text" v-model="employee.salary" style="width: 100%"/>
              <label for="salary">salary</label>
            </span>
      <template #footer>
        <Button label="save" icon="pi pi-check" @click="save"/>
        <Button label="close" icon="pi pi-times" @click="closeModal" class="p-button-secondary"/>
      </template>
    </Dialog>
  </div>
</template>

<script>
import EmployeeService from "../service/EmployeeService";

export default {
  name: 'Employee',
  data() {
    return {
      employees: null,
      employee: {
        id: null,
        firstName:null,
        lastName: null,
        department: null,
        salary: null
      },
      selectedEmployee: {
        id: null,
        firstName: null,
        lastName: null,
        department: null,
        salary: null
      },
      items: [
        {
          label: "New",
          icon: "pi pi-fw pi-plus",
          command: () => {
            this.showSaveModal();
          }
        },
        {
          label: "Edit",
          icon: "pi pi-fw pi-pencil",
          command: () => {
            this.showEditModal();
          }
        },
        {
          label: "Delete",
          icon: "pi pi-fw pi-trash",
          command: () => {
            this.delete();
          }
        },
        {
          label: "Refresh",
          icon: "pi pi-fw pi-refresh",
          command: () => {
            this.getAll();
          }
        }
      ],
      displayModal: false,
    }
  },
  employeeService: null,
  created() {
    this.employeeService = new EmployeeService();
  },
  mounted() {
    this.employeeService.getAll().then(data => {
      this.employees = data.data;
      console.log(this.employees);
    })
  },
  methods: {
    showSaveModal() {
      this.displayModal = true;
    },
    showEditModal() {
      this.employee = this.selectedEmployee;
      this.displayModal = true;
    },
    getAll() {
      this.employeeService.getAll().then(data => {
        if (data.status === 200) {
          this.$toast.add({
            severity: "success",summary: "Success", detail: "Data submitted", life: 3000
          })
        }
        this.employees = data.data;
      })
    },
    delete(){
      if (confirm("Are you sure?")) {
        this.employeeService.delete(this.selectedEmployee.id).then(data => {
          if (data.status === 200) {
            this.$toast.add({
              severity: "success", summary: "Success", detail: "Employee deleted", life: 3000
            });
            this.getAll();
          }
        });
      }
    },
    save(){
      this.employeeService.save(this.employee).then(data => {
        if (data.status === 201) {
          this.displayModal = false;
          this.employee = {
            firstName: null,
            lastName: null,
            department: null,
            salary: null
          };
          this.getAll();
        }
      });
    },
    closeModal() {
      this.displayModal = false;
    }
  }
};
</script>

<style>

</style>
