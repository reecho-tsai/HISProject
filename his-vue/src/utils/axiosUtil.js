import axios from 'axios'
import { Loading, Message, MessageBox } from 'element-ui'
import router from '@/router'
axios.defaults.baseURL = 'http://127.0.0.1:8088'

// 拦截请求，添加请求头
axios.interceptors.request.use(config => {
  if (config.url !== '/login' && config.url !== '/sendCode' && config.url !== '/loginByCode') {
    const token = window.sessionStorage.getItem('token')
    if (!token) {
      MessageBox.alert('登录信息已过期，请重新登录', '登录过期', {
        confirmButtonText: '跳转到登录页',
        callback: action => { router.push('/') }
      })
      return
    }
    config.headers.Authorization = token
  }
  // 在最后必须 return config
  return config
})

axios.interceptors.response.use(config => config,
  err => {
    // console.dir(err)
    if (err.response.status === 404) {
      Message.error('无法请求到' + err.response.config.baseURL + err.response.data.path)
    }
  }
)

const loadMsgGet = {
  lock: true,
  text: '数据加载中',
  spinner: 'el-icon-loading',
  background: 'rgba(255, 255, 255, 0.7)'
}

const loadMsgPut = {
  lock: true,
  text: '数据提交中',
  spinner: 'el-icon-loading',
  background: 'rgba(255, 255, 255, 0.7)'
}

const callbackhandler = (promise, callback, loading) => {
  promise.then(
    response => {
    // console.log(response)
      if (response.data.status === 200) {
        // Message.success(response.data.message)
        callback(response.data)
      } else {
        Message.error(response.data.message)
      }
    }
  ).catch(() => {
    Message.error('服务器发生错误，正在联系程序员小哥哥排查中.....')
  }).finally(() => {
    loading.close()
  })
}

export default {
  get (url, callback, params = {}) {
    const loading = Loading.service(loadMsgGet)
    callbackhandler(axios.get(url, { params: params }), callback, loading)
  },

  post (url, callback, data = {}) {
    const loading = Loading.service(loadMsgPut)
    setTimeout(() => {
      loading.close()
    }, 10000)
    callbackhandler(axios.post(url, data), callback, loading)
  },

  put (url, callback, data = {}) {
    const loading = Loading.service(loadMsgPut)
    setTimeout(() => {
      loading.close()
    }, 10000)
    callbackhandler(axios.put(url, data), callback, loading)
  },

  del (url, callback, params = {}) {
    MessageBox.confirm('确定要删除数据吗?', '删除操作', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(() => {
      const loading = Loading.service(loadMsgPut)
      setTimeout(() => {
        loading.close()
      }, 10000)
      callbackhandler(axios.delete(url, { params: params }), callback, loading)
    }).catch(err => err)
  },
  // 讲原生的axios 暴露 ，自定义使用
  http: axios
}
