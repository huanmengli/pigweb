import request from '@/utils/request'

// 查询smallPig列表
export function listPig(query) {
  return request({
    url: '/smallPig/pig/list',
    method: 'get',
    params: query
  })
}

// 查询smallPig详细
export function getPig(smallPigId) {
  return request({
    url: '/smallPig/pig/' + smallPigId,
    method: 'get'
  })
}

// 新增smallPig
export function addPig(data) {
  return request({
    url: '/smallPig/pig',
    method: 'post',
    data: data
  })
}

// 修改smallPig
export function updatePig(data) {
  return request({
    url: '/smallPig/pig',
    method: 'put',
    data: data
  })
}

// 删除smallPig
export function delPig(smallPigId) {
  return request({
    url: '/smallPig/pig/' + smallPigId,
    method: 'delete'
  })
}
