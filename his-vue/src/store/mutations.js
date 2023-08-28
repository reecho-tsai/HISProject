/*
包含n个用于直接更新状态的方法的对象模块
 */
import { TOGGLE_COLLAPSE, GET_ALL_CONSTANT_TYPE, GET_ROLES, GET_REGISTLEVEL, GET_DEPARTMENT } from './mutation-types'

export default {
  [TOGGLE_COLLAPSE] (state) {
    state.isCollapse = !state.isCollapse
  },
  [GET_ALL_CONSTANT_TYPE] (state, constantTypes) {
    state.constantTypes = constantTypes
    // console.log(state.constanTypes)
  },
  [GET_ROLES] (state, roles) {
    state.roles = roles
  },
  [GET_REGISTLEVEL] (state, register) {
    state.register = register
  },
  [GET_DEPARTMENT] (state, department) {
    state.department = department
  }
}
