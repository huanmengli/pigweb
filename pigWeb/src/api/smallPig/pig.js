import request from '@/utils/request'

// 查询smallPig列表
export function listSmallPig(query) {
  return request({
    url: '/smallPig/pig/list',
    method: 'get',
    params: query
  })
}

// 查询smallPig详细
export function getSmallPig(smallPigId) {
  return request({
    url: '/smallPig/pig/' + smallPigId,
    method: 'get'
  })
}

// 新增smallPig
export function addSmallPig(data) {
  return request({
    url: '/smallPig/pig',
    method: 'post',
    data: data
  })
}

// 修改smallPig
export function updateSmallPig(data) {
  return request({
    url: '/smallPig/pig',
    method: 'put',
    data: data
  })
}

// 删除smallPig
export function delSmallPig(smallPigId) {
  return request({
    url: '/smallPig/pig/' + smallPigId,
    method: 'delete'
  })
}
