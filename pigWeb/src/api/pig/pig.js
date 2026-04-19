import request from '@/utils/request'

// 查询pig列表
export function listPig(query) {
  return request({
    url: '/pig/pig/list',
    method: 'get',
    params: query
  })
}

// 查询pig详细
export function getPig(pigId) {
  return request({
    url: '/pig/pig/' + pigId,
    method: 'get'
  })
}

// 新增pig
export function addPig(data) {
  return request({
    url: '/pig/pig',
    method: 'post',
    data: data
  })
}

// 修改pig
export function updatePig(data,id) {
  return request({
    url: '/pig/pig?id='+id,
    method: 'put',
    data: data
  })
}

// 删除pig
export function delPig(pigId) {
  return request({
    url: '/pig/pig/' + pigId,
    method: 'delete'
  })
}
