/*
包含n个用于间接更新状态的方法的对象模块
 */
import { TOGGLE_COLLAPSE, GET_ALL_CONSTANT_TYPE, GET_ROLES, GET_REGISTLEVEL, GET_DEPARTMENT } from './mutation-types'
import axios from 'axios'

export default {
  toggleCollapse ({ commit }) {
    commit(TOGGLE_COLLAPSE)
  },
  async getConstantTypes ({ commit }) {
    const { data: res } = await axios.get('/constantTypes/all')
    if (res.status === 200) {
      commit(GET_ALL_CONSTANT_TYPE, res.data)
    }
  },
  async getRoles ({ commit }) {
    const { data: res } = await axios.get('/roles/all')
    if (res.status === 200) {
      commit(GET_ROLES, res.data)
    }
  },
  async getRegisterLevel ({ commit }) {
    const { data: res } = await axios.get('/registLevels/all')
    if (res.status === 200) {
      commit(GET_REGISTLEVEL, res.data)
    }
  },
  async getDepartment ({ commit }) {
    const { data: res } = await axios.get('/departments/all')
    if (res.status === 200) {
      commit(GET_DEPARTMENT, res.data)
    }
  }
}
